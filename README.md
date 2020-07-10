# FitLift

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
2. [Timeline](#Timeline)
3. [Wireframes](#Wireframes)
4. [Schema](#Schema)

## Overview
### Description
**FitLift** is an all around fitness journal app. The easy to use and customizable interface provides fitness goers an easy way to log their workouts. Features include the ability to automatically calculate the total amount of weight lifted, connection to 3rd party fitness wearables (Fitbit, Google fit, etc.), ability to add friends and compete, and a nutrition tracker section.

### App Evaluation
- **Category:** Health & Fitness
- **Mobile:** This app will initally be mobile focused and incorporate features such as camera use, location tracking, and push notifications
- **Story:** The value of this story is very clear. It is mainly focused on creating an easy way to track your fitness progress, while also including features to gamify this process with the hope of creating more motivation
- **Market:** The market will be fairly large since there are a lot of people who are focused on fitness and health. The specific focus will be on those who lift weights
- **Habit:** The app will become something that aids workouts and will build a habit to be used whenever someone exercises
- **Scope:** The app can be difficult to build, especially with some of the stretch features, but even the stripped down version of the app will still bring values to those who are interested in fitness

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* Users can keep a workout journal that includes entries for workout name, amount of weight being lifted, and the amount of reps
* User can quickly see the total amount of weight pushed for a given exercise
* User can scroll through previous workouts
* User can scroll through previous food journals
* User can create an account
* User can login
* User can keep a food journal with ability to track meals ate as well as attach a picture of the meal (similar to workout journal structure)
* User can swipe for new workout/food journal entry
* (SDK integration (Google fit?))
* (Visually appealing)

**Optional (Stretch) Nice-to-have Stories**

* User can add friends and see their fitness stats
* User can search for other users
* User can compete against friends
* User can scroll through friend's stats
* User can create and edit profile page
    * Profile Picture
    * Brief Stats
    * Bio
* User can receive notifications when close to breaking weight lifting personal records
* User can calculate macro nutrient values for food journal
* User can see recipe ideas
* User can receive notifications when a friend is exercising
* User can receive notification for when a friend passes their score
* (Food scanning API for food journal)

### 2. Screen Archetypes

* Creation
   * Users can keep a workout journal that includes entries for workout name, amount of weight being lifted, and the amount of reps
   * Users can keep a food journal to track meals ate during the day and quickly calculate
* Registration Screen
   * User can create an account
   * User can set profile picture
   * User can create bio
* Login Screen
    * User can login
* Stream
    * User can scroll through previous workouts
    * User can scroll through previous food journals
    * User can scroll through friend's stats
* Search
    * User can search for other users

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Workouts
* Food
* Friends

**Flow Navigation** (Screen to Screen)

* Login Screen
   * Workouts
* Registration Screen
   * Workouts
* Workout Creation Screen
    * Workouts
* Food Journal Creation Screen
    * Food
* Add Friends
    * Friends
* Start Competition
    * Friends
* Take picture of meal
    * Food
* User profile screen
    * Workouts
* Search users
    * Friends

## Timeline

### Week 1 
* Account creation page
* Login page
* Fragment for workout journal
    * See total amount of work done for workout (total weight)
    * Able to scroll through past workouts

### Week 2
* Fragment for food journal
    * Create journal entries similar to workout journal
    * Add ability to attach an image to each entry
    * Add entries for nutritients with summation of values
    * Able to scroll through past meals

### Week 3
* SDK integration
    * Possibilities:
        * Google Fit
        * Fitbit
        * Google maps
* User profile page 
    * Profile pic
    * Bio
    * Workout stats
* Ability to search other users and 'follow' them

### Week 4
* Social fragment
    * See friends that you are following
    * User can scroll through friends stats/journal entries
* User can start competition with friends
    * Set time period for competition
    * Set the competing factor (weight lifted/miles ran)
    * User can receive notification when friend passes their score
    * Provide a victory popup for the user that wins the competition

### Week 5
* User will recieve notification when close to breaking a record
* User will recieve notification when a friend is exercising
* API to scan barcodes to enter food items in food journal
* Visual polish



## Wireframes

- Basic iteration with a few stretch stories


<img src='https://i.imgur.com/UHbtnmB.jpg'>


## Schema 
model->table (java class)
property->column

### Models

**Model: User**

| Propery  | Type     | Description |
| -------- | -------- | -------- |
| objectId | String | unique id for the journal (default field)|
| userName | String | unique id for user |
|profilePic| File | img uploaded by user |
| woStats  | array of pointers to Workout Journals | gather weight/reps and calculate average |
| mealStats | array of pointers to Meal Journals | gather calorie/nutrients and calculate average |


**Model: Workout Journal** 

| Property | Type     | Description |
| -------- | -------- | -------- |
| objectId | String   | unique id for the journal (default field) |
| woTitle    | String | title of workout journal |
| createdAt| DateTime | date when post is created (default field) |
| updatedAt | DateTime | date when post is last updated (default field) |
| woName   | String | name of workout |
| weight   | Number | amount of weight used |
| reps     | Number | amount of reps done |

**Model: Meal Journal**

| Property | Type | Description |
| -------- | -------- | -------- |
| objectId | String | unique id for the journal (default field) |
| mealTitle | String | title of meal journal |
| createdAt | DateTime | date when post is created (default field) |
| updatedAt | DateTime | date when post is last updated (default field) |
| mealDscrp | String | description of meal entered by user |
| nutrient | String | nutrient name entered |
| amount | String | amount of a nutrient |
| mealPic | File | photo of meal |

**Model: Friend**

| Property | Type | Description |
| -------- | -------- | -------- |
| objectId | String | unique id for a friend (default field) |
| woStats | Array of pointers to workout journals of friends | Visual of workout stats of friend |
| meal stats | Array of pointers to meal journals of friends | visual of meal stats of friend |
| userName | pointer to friend (User type) | points to User table of friend



### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
