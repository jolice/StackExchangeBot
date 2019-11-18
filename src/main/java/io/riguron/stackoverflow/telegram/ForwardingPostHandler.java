package io.riguron.stackoverflow.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import io.riguron.stackoverflow.api.query.Question;
import io.riguron.stackoverflow.polling.NewPostHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class ForwardingPostHandler implements NewPostHandler {

    private int receiver;

    private Queue<Question> queue = new ConcurrentLinkedQueue<>();

    private String deletePayload;

    private TelegramBot telegramBot;

    @Autowired
    public ForwardingPostHandler(TelegramBot telegramBot, @Value("${receiver}") int receiver, @Value("${delete.payload}") String deletePayload) {
        this.telegramBot = telegramBot;
        this.receiver = receiver;
        this.deletePayload = deletePayload;
    }

    @Scheduled(fixedDelay = 1000)
    public void processNextQuestion() {
        // Safe, no check-then-act race. Calls are sequential, queue is polled by single thread
        if (!queue.isEmpty()) {
            this.prepareAndSend(queue.poll());
        }
    }

    @Override
    public void newPost(Question question) {
        queue.offer(question);
    }

    private void prepareAndSend(Question question) {
        SendMessage sendMessage = new SendMessage(receiver, formatMessage(question));
        sendMessage.replyMarkup(new InlineKeyboardMarkup(
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("Open").url(question.getLink()),
                        new InlineKeyboardButton("Delete").callbackData(deletePayload)
                }
        ));
        telegramBot.execute(sendMessage);
    }


    private String formatMessage(Question question) {
        return question.getTitle() + "\n\n" + question.getLink();
    }
}
