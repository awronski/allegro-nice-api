# allegro-nice-api
Flexible and easy to use unofficial **java allegro sdk** focused on trading operation on allegro platform.
Supports **BOTH [Allegro WebApi](https://allegro.pl/webapi)** and 
**[Allegro Rest Api](https://developer.allegro.pl/)**. 

## Requirements
- Java 8
- [BD lib](https://github.com/awronski/bd)

## Install
```
git clone https://github.com/awronski/allegro-nice-api.git
cd allegro-nice-api
mvn package install
```
Tests are skipped by default during packaging because of bugs in allegro test environment.
If you want to run the test you have to rename ```test-credentions.template``` to ```test-credentions.properties``` and set the credentials.
Than run the tests:
```
mvn compile test -P testprof
```

# Usage

## Initialize object
```java
int countryId = 1;
Configuration conf = new Configuration(countryId);

Credentials cred = new Credentials(
        clientId, 
        username, 
        password, 
        webApiKey,
        restClientId,
        restClientSecret,
        restClientApiKey,
        restRedirectUri
        );

IAllegroNiceApi allegro = AllegroNiceApi.Builder()
        .conf(conf)
        .cred(cred)
        .build();
```

## Login for WebApi
```java
allegro.login();
```

## Login for RestApi
To get the code you need to authorize your user with [allegro](https://developer.allegroapi.io/auth/#user).

```java
allegro.restLogin(code);
```

### Refrsh RestApi tokens
```java
allegro.refreshRestApiSession();
```

## Get user's auctions
```java
Observable<Auction> auctions = api.getAuctions();
```

## Get list of deals
```java
long startingPoint = 0;
Observable<Deal> deals = api.getDeals(startingPoint);
```

## Get list of payments with clients data
```java
Observable<Payment> payments = getPayments(deals);
```

## Subscribe to journal
You can subscribe to user's journal using ```api.getSiteJournal(startingPoint)```.
Like above methods this one returns [Observable](http://reactivex.io/documentation/observable.html) object too.
```java
long startingPoint = 0;
api.getSiteJournal(startingPoint)
    .subscribe(
        j -> System.out.println("on next: " + j),
        t -> System.out.println("on error: " + t),
        () -> System.out.println("on complete")
    );
```
Read more about [RxJava](https://github.com/ReactiveX/RxJava).

## Create new auction
To create new auction you need to create list of auctions fields and [sales conditions](https://developer.allegro.pl/new_desc/).
Id of the field is the same as on the allegro service. Check ```getSellFormFields(categoryId)``` to get all possible fields for a given category.

```java
List<NewAuctionField> fields = new ArrayList<>();
fields.add(new NewAuctionField(1, FieldType.Type.STRING, "Auction title"));
[...]

SalesConditions cond = new SalesConditions("XXX", "YYY", "ZZZ");
NewAuction newAuction = new NewAuction(fields, cond);

CreatedAuction created = api.createNewAuction(newAuction);
```

## Change auction's quantity and price, even if auction has offers
```java
ChangedQty changedQty = api.changeQty(itemId, 5);
ChangedPrice changedPrice = api.changePrice(itemId, 1.99);
```

## Change or finish auctions
```java
ChangedAuctionInfo changedAuctionInfo = api.changeAuctions(itemId, fields);
List<FinishAuctionFailure> failures = api.finishAuctions(itemsIds);
```

License
=======

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
