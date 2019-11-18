# StackExchange Bot

This is a configurable Telegram bot that sends you notifications about new questions asked on [StackExchange](https://stackexchange.com/) projects.

![](https://i.imgur.com/NHNmpzm.png)

## Technology

This application utilizes Stack Exchange API to query fresh questions (by the creation date).   
Without an application token, the API allows up to 300 requests from single IP address.   
This bot performs API requests anonymously, so while configuring a bot, keep this limit in mind.

## Building

First, cllone the directory:

```bash
git clone git@github.com:riguron/StackExchangeBot.git
cd StackExchangeBot
```

And build a bot with [Spring Boot Maven Plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins.html):

```bash
mvn clean package spring-boot:repackage
```

The executable JAR will be built under the ```target``` directory.

## Configuration

In order to use bot, you must put `application.yml` configuration file under the directory with bot's executable JAR. The configuration must look as follows:

```yaml
sites: stackoverflow,meta
tags: java,spring
ignore: tags,to,ignore
polling:
   delay: 300000

receiver: YOUR_TELEGRAM_ID
delete:
   payload: delete
bot:
  token: BOT_TOKEN
```

The file structure must look as follows:

```bash
├── application.yml
└── bot.jar
```


### Options

#### `sites`

Sites that will be queried for the new questions. Unfortunately, Stack Exchange API doesn't allow for specifying multiple sites
in one request, so `N` requests are required to get new questions from `N` Stack Exchange projects.

Therefore, specifying `N` sites will result in decreasing the possible polling frequency due to API limits. While 1 site can be queried
300 times per 24 hours, 2 sites may only be queried 150 times each per 24 hours. **The more sites you specify, the less is possible
polling frequency** you may set. It's recommended to *specify only one site*, then the queries may be performed approximately every 5 minutes.

#### `tags`

Tags you want to watch, i.e the filter for the new questions. Unfortunately, specifying multiple tags produces `AND` filter rather than `OR` one.
Thus, if you specify both java and spring tags, API returns only questions tagged both with spring and java, and not ones tagged 
with spring, java or both. So **the more tags you specify at once, the less results you get**. It's recommended to *specify only one tag*, 
for example, your programming language.

#### `ignore`

You won't receive questions that are tagged with at least one of these tags. You may specify as many tags as you need.

#### `polling.delay` 

An option for a scheduler that performs API calls for querying new questions (in milliseconds). A recommended value for one site is **300000**, i.e 300 seconds (5 minutes).
As there are 86400 seconds in a day and the API limit is 300 requests per day, it's allowed to perform requests not more often
than every 288 seconds. 

Remember that each specified site involves one additional API request, soo the initial polling delay must be increased by two for each new
site. 

#### `receiver`

Your *Telegram ID*. To find it out, take advantage of [@JsonDumpBot](t.me/jsondumpbot). Send any message to it, any it will respond you
with a JSON containing the following element:

```json
"from": {
      "id": 42,
      "is_bot": false,
      "first_name": "Your Name",
      "username": "your_nick_name",
      "language_code": "en"
    },
```

**42** is your Telegram ID, the value for the `receiver` property.

#### `delete.payload`

This option accepts any non-null value, you may set it to whatever you want. However, you'd better *don't modify it* and leave as it is.

#### `bot.token`

To use this bot, you must [create a Telegram bot](https://docs.microsoft.com/en-us/azure/bot-service/bot-service-channel-connect-telegram?view=azure-bot-service-4.0) that will send you the notifications about new questions.
After the creation, copy the bot's token to the configuration. 

Note that you must **start a dialog with a bot** in order to receive notifications. Telegram doesn't allow bots to send messages to the
users that don't have an active dialog with a bot!

## Troubleshooting

Q: An application doesn't start.   

- Make sure that ```application.yml``` resides in the same directory with your executable JAR and that it's configured in a proper
way, with accordance to the instructions. 
- Check whether you don't miss some application property.

Q: I don't receive any messages from my bot. 

- Make sure that you've started a dialog with your bot. If you're new to Telegram bots, you may want to read the [guide](https://tutorials.botsfloor.com/how-to-build-telegram-powered-bots-78c092298df6).
- There might be no new questions or you've specified too strict filters. Try to adjust the tags and ignored tags. 
- Stack Exchange API may be down for the maintenance. The internal server error exception will be fired, the stack trace will be displayed.
- Check application logs for other kinds of exceptions.




