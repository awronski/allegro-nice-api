# allegro-nice-api
Flexible and easy to use java api for allegro service.

## Initialize object
    :::java
        int countryId = 1;
        Configuration conf = new Configuration(countryId);
        Credentials cred = new Credentials("username", "password", "key");

        IAllegroNiceApi allegro = AllegroNiceApi.Builder()
                                    .conf(conf)
                                    .cred(cred)
                                    .build();

## Login
    :::java
        allegro.login();

## Get system messages
    :::java
        List<AllegroMessage> msgs = allegro.getAllMessagesallegro(LocalDateTime.now().minusDays(30));

## _Work in progress_
