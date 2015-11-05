# allegro-nice-api
Flexible and easy to use java api for allegro service focused on trading operation on allegro platform.

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
long clientId = 44556677;
Configuration conf = new Configuration(countryId);
Credentials cred = new Credentials(clientId, "username", "password", "key");

IAllegroNiceApi allegro = AllegroNiceApi.Builder()
        .conf(conf)
        .cred(cred)
        .build();
```

## Login
```java
allegro.login();
```

## Get user's auctions
```java
Observable<Auction> auctions = api.getAuctions();
```

## Get list of deals
```java
long startingPoint = 0;
Observable<Deal> deals =  api.getDeals(startingPoint);
```

## Get list of payments with clients data
```java
Observable<Payment> payments - getPayments(deals);
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
To create new auction you need to create list of auctions fields.
Id of the field is the same as on the allegro service. Check ```getSellFormFields(categoryId)``` to get
all possible fields for a given category.

```java
List<NewAuctionField> fields = new ArrayList<>();
fields.add( new NewAuctionField(1, FieldType.Type.STRING, "Auction title") );
[...]
CreatedAuction created = api.createNewAuction(fields);
```

## Change auction's quantity
```java
ChangedQty changedQty = api.changeQty(itemId, 5);
```

## Change or finish auctions
```java
ChangedAuctionInfo changedAuctionInfo = api.changeAuctions(itemId, fields);
List<FinishAuctionFailure> failures = api.finishAuctions(itemsIds);
```

## Get and create feedbacks
```java
int counter = api.getWaintingFeedbackCounter();
Observable<WaitingFeedback> api.getWaitingFeedbacks();

List<CreateFeedback> create = new ArrayList<>(..)
List<CreatedFeedback> =  api.createFeedbacks(create)
```

## Get system messages
```java
List<AllegroMessage> msgs = allegro.getAllMessages(LocalDateTime.now().minusDays(30));
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
