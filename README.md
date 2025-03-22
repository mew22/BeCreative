# "BeCreative" Android App

This application has been developed as part of a technical test for a job opportunity at BeReal.

Here is the skill test statement:

Welcome. This challenge evaluates your product vision, business sense, and technical skills.

At BeReal, we aim to create an app that can reach the world's top 10 most-used apps.
Photo sharing is a category with a track record of supporting big apps. You’ll be creating a rough prototype of an app that at least contains such features.

You have 24 to 48 hours to complete the assignment. You should take half of the time to establish your product vision and the remaining one to code.

Your task is to create a prototype for an innovative social photo-sharing app that can reach 1M Daily active users (DAU). We ask you to:

- Think of a powerful core loop and explain it in a few bullet points in a PDF file:
    - What’s your product vision?
    - What’s your product job to be done?
    - How’s your product going to reach 1M DAU? We’re not asking for a complicated model, but a simple reality check.
- Build a prototype of your product:
    - Implement core features and functionality.
    - Make a delightful user experience. We’d rather have less functionality well executed than a flurry of half-finished features.
    - Write clean code.

We will evaluate you on:

- Quality of the idea: can you convince us that you’re going to build a 1M DAU app? Because that’s what we’re hiring for. You can be the best technician if you work on a small vision, you’ll make a small app.
- Technical proficiency and speed.
- Quality of UI/ UX.

## Product vision

BeCreative is BeReal with more challenge. Each days, a AI generated user-distinct challenge is spawn for each user. 

### 1st phase:
You are allowed to see your friends challenge implementation and react to them once you finished your challenge.

Example of challenge:
- Snap a shadow or silhouette around you!

● Core loop

Daily prompt challenge:
Every user receives a random prompt at a different time each day.
Users have 3 minutes to snap a photo, creating a sense of urgency.

No editing allowed:
Users must take a live photo (no gallery uploads).
No filters, no retouching—just the raw, authentic moment.

Engagement:
Once posted, users can see their friends snaps for the day.
React with emojis or voice reactions.

Virality:
Users can’t see others snaps until they post their own, encouraging participation.
Streaks & leaderboards reward daily engagement.
Friends can invite others to join and compare real-time moments.

### 2nd phase:
Enable users to hide their challenge prompt to allow others users to guess it or create a photo chain.

##### Usecase 1: Try to guess other challenge
Users see the photo in their feeds and want to guess the original challenge. 
They post an interpretation of the challenge as text. This would appears in others user feed.
Once posted, it is possible to comment and react.

● Core loop:

Guess a challenge:
Users can try to guess a challenge by contributing a description of the photo

Engagement:
Once posted, users can see their friends guess.
React with emojis or voice reactions.

Virality:
Gamification encouraging participation.

##### Usecase 2: Photo chain conversation
Users see the photo in their feeds and want to answer the photo with a photo to create a photo chain. This would appears in others user feed.
This loop could be repeated at maximum 10 times.
The chain will be reveled when it reached its maximum (10 photos) or when the next daily challenge occurs.
Once reveled, it is possible to comment and share the chain.
Subchain can exists.

● Core loop

Connection:
Users upload a photo that relates to the last one in the chain.
They can add a caption or emoji reaction to explain the connection.
Once posted, others can continue the chain with their own interpretations.

Virality:
Each PhotoChain can "branch" when multiple users contribute different responses.
Popular chains get featured on a discovery page, encouraging engagement.

Sharing:
Users can share a photo chain link on social media.
Friends must contribute a photo to unlock the full chain, driving viral growth.

## State of the application

First of all, I would like to point out that the application is a prototype and is incomplete in its current state.
Only 1st phase and usecase 1 of 2nd phase has been implemented and I did not manage to write unit tests nor to implement a real backend environment in such a short time.
Enough of what is missing, let's talk about what has been done so far.

The application in its current state can display a feed representing friends post and a daily mission banner (that spawn between 10 and 20s) on the home screen.
Once the daily mission is spawn, feed get blurred until user complete its mission.

## Quality measurements

In order to implement quality standards, I added to the project some Gradle task for:
- Linter and code smell checker, using KtLint and Detekt (./gradlew check and ./gradlew detekt)

## Technical choices

I choose to use Gradle KTS with build convention structure to manage the project configuration, while it is a powerful tool that allow autocompletion, it also provide all the features of Kotlin language.

I used Koin to manage the dependency injection. This library has its pros (simplicity, reduce verbosity, multiplatform friendly for future plans) and of course its cons (error prone because of runtime resolution).

I used Kotlin Flow to communicate the data between the different layers because it is a native Kotlin API and offers better integration with "reactive" operators.
It also avoid to have android objects like LiveData in the domain and make testing easier.

I used Jetpack Compose declarative UI framework coupled with the navigation library to navigate inside features screen.

I chose Coil to show the internet images as it is reliable and reduces the complexity of such operations.

## Architectural decisions

For the presentation part, I like to implement MVI pattern, that explicitly define Event and State of the UI with a viewModel to coordinate them.
Then I tried to implement the Clean Architecture following the SOLID principles.
It makes the applications scalable in different way. For instance, there is no business logic within the presentation layer and the code is, as much as possible, decoupled from the Android framework
like all :data and :domain modules remain plain Kotlin modules (which makes it more easily testable).

Here I choose to split my module structure by feature, and then by layer (domain, data, ui) in order to be more explicit about the separation of concerns.
There are also following reasons that convince me to drive my architecture by feature:

- Higher Modularity
  Package-by-feature has packages with high cohesion, high modularity, and low coupling between packages.

- Easier Code Navigation
  Maintenance programmers need to do a lot less searching for items, since all items needed for a given task are usually in the same directory. Some tools that encourage package-by-layer use package naming conventions to ease the problem of tedious code navigation. However, package-by-feature transcends the need for such conventions in the first place, by greatly reducing the need to navigate between directories.

- Higher Level of Abstraction
  Staying at a high level of abstraction is one of programming's guiding principles of lasting value. It makes it easier to think about a problem, and emphasizes fundamental services over implementation details. As a direct benefit of being at a high level of abstraction, the application becomes more self-documenting: the overall size of the application is communicated by the number of packages, and the basic features are communicated by the package names. The fundamental flaw with package-by-layer style, on the other hand, is that it puts implementation details ahead of high level abstractions - which is backwards.

- Separates Both Features and Layers
  The package-by-feature style still honors the idea of separating layers, but that separation is implemented using separate classes. The package-by-layer style, on the other hand, implements that separation using both separate classes and separate packages, which doesn't seem necessary or desirable.

- Minimizes Scope
  Minimizing scope is another guiding principle of lasting value. Here, package-by-feature allows some classes to decrease their scope from public to package-private. This is a significant change, and will help to minimize ripple effects. The package-by-layer style, on the other hand, effectively abandons package-private scope, and forces you to implement nearly all items as public. This is a fundamental flaw, since it doesn't allow you to minimize ripple effects by keeping secrets.

- Better Growth Style
  In the package-by-feature style, the number of classes within each package remains limited to the items related to a specific feature. If a package becomes too large, it may be refactored in a natural way into two or more packages. The package-by-layer style, on the other hand, is monolithic. As an application grows in size, the number of packages remains roughly the same, while the number of classes in each package will increase without bound.


## Real work environment and improvements reflexion

I tried to split my commits so they are reviewable but it is possible to split in even smaller chunks of code.
In a real work environment, we probably would have groomed the work to split it into small subtasks with some acceptance criteria for each.

There are some improvements that I would like to add later:
- Implement the backend to allow the app to run with real data
- Implement phase 2 described in product section
- We could imagine to implement Guessing as a chain of alternate text and image (like GarticPhone)
- Implement unit testing
- Move to KMP and CMP to target multiplatform

## Source of inspiration

- Multiple way of defining Clean Architecture Layers: https://proandroiddev.com/multiple-ways-of-defining-clean-architecture-layers-bbb70afa5d4a
- Dynamic feature delivery: https://developer.android.com/guide/playcore/feature-delivery
- Uncle bob screaming architecture: https://levelup.gitconnected.com/what-is-screaming-architecture-f7c327af9bb2
- Modular patterns: https://hackernoon.com/applying-clean-architecture-on-web-application-with-modular-pattern-7b11f1b89011
- Package by features with bounded contexts: https://reflectoring.io/java-components-clean-boundaries/
- 5 most populars package structures: https://www.techyourchance.com/popular-package-structures/

## Source code description

```yaml
- /app
    # Main activity
    - MainActivity.kt
    # Application class
    - App.kt
    # Inter-Feature navigation
    - AppNavHost.kt
    # Injection configuration
    - Injection.kt
    # Database definition
    - Database.kt
- /build-logic
    # Configuration for Application module
    - AppPlugin
    # Configuration for Application module with compose
    - ComposeAppPlugin
    # Configuration for Android Library module
    - AndroidLibPlugin
    # Configuration for Android Library module with compose
    - ComposeLibPlugin
    # Configuration for Koltin Library module
    - KotlinLibPlugin
    # Configuration for Detekt
    - DetektPlugin
# All the common, global and shared material and configuration
- /core
    # Shared UI component and design system
    - /ui
    # Common Kotlin tools like Flow, coroutine or Result extension
    - /common
    # Shared monitoring tool for crashlytics, logger etc
    - /monitoring
        # Abstraction interface (should be a Kotlin only module)
        - /gateway
        # Implementation (should be a Android library module)
        - /implementation
    # Common db helper to create database
    - /db
        # Abstraction interface (should be a Kotlin only module)
        - /gateway
        # Implementation (should be a Android library module)
        - /implementation
# Features
- /feature
    # Feature module corresponding to feed feature (display feed and react to it)
    - /feed
        # Plain Kotlin module responsible for containing everything related to external services like databases, remote services, device apis, data providers
        - /data
        # Plain Kotlin module that hold business rules, entities, failures, value objects and repositories abstractions
        - /domain
        # Android module that hold compose views, fragments, views, viewModels
        - /ui
        # Android module that act as an umbrella module for all data, domain and ui of the feature. Hold dependency injection and routes.
        - /lib
    # Feature module corresponding to mission feature (display mission and achieve it)
    - /mission
        # Plain Kotlin module responsible for containing everything related to external services like databases, remote services, device apis, data providers
        - /data
        # Plain Kotlin module that hold business rules, entities, failures, value objects and repositories abstractions
        - /domain
        # Android module that hold compose views, fragments, views, viewModels
        - /ui
        # Android module that act as an umbrella module for all data, domain and ui of the feature. Hold dependency injection and routes.
        - /lib
    # Feature module corresponding to home feature (display aggregate of other features)
    - /home
        # Android module that hold compose views, fragments, views, viewModels
        - /ui
        # Android module that act as an umbrella module for all data, domain and ui of the feature. Hold dependency injection and routes.
        - /lib
```

## Screenshots

<img src="https://github.com/mew22/becreative/blob/main/screenshot/screenshot_1.png"/>
<img src="https://github.com/mew22/becreative/blob/main/screenshot/screenshot_2.png"/>
<img src="https://github.com/mew22/becreative/blob/main/screenshot/screenshot_3.png"/>
<img src="https://github.com/mew22/becreative/blob/main/screenshot/screenshot_4.png"/>
<img src="https://github.com/mew22/becreative/blob/main/screenshot/screenshot_5.png"/>
<img src="https://github.com/mew22/becreative/blob/main/screenshot/recording.gif"/>