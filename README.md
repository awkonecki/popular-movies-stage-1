# Project 2 - Popular Movies Stage 1
## Project Description
This is the second project for the Android development track associated with the Udacity Android development.

## Project Goals
The goals listed below are a copy from the Udacity project page.

- Present the user with a grid arrangement of movie posters upon launch.
- Allow your user to change sort order via a setting:
  - The sort order can varry based on supported types (popularity, rating, name etc).
- Allow the user to tap a poster and transition to a details screen with additional information.
  - original title
  - movie poster image thumbnail
  - plot synopsis (overview)
  - user rating (vote_average)
  - release date

This project is to showcase developing a new project from scratch given a rough set of desired ideas.  Due to the content this will require the developer to connect to an external API, handle background processes, support an interactive UI

## Design Stages
This a a project from scratch and although there are some rough guidelines provided in terms of capability I think it is worth while to write out the areas of focus for this project.

Since this is a two part project will look at both rubics to determine the core set of features that will be needed.

### Network
- The application must be capable of retrieveing movie information form the `themoviedb` service using a valid API key.
- The key requests are as follows
  - `/movie/popular` - based on the sort criteria of user selection
  - `/movie/top_rated` - based on the sort criteria of user selection
  - `/movie/{id}/videos` - videos that are related to the selected movie
  - `/movie/{id}/reviews` - reviews of the selected movie
- Due to these being network activities that can take an unknown amount of time, the fetching of any data from the service should occur in a background thread / task.
- Due to these being background tasks / threads need to handle the case of data deliverly within the application life-cycle manager.
- will also need to support the `find` query for going beyond the popular definition only.

#### Approach
- make sure can support each type of API query
- determine how to handle error events with the network stack

### Data Persistance
- minimum requirement of saving the user's favorite `title` & `id` for each movie
- this requires use of SQLite DB and content provider to such information
- favorite selection will likely need to store the other movie meta data in order to prevent retrival of this data again from the network.
- user action of un-favorite something should lead to removal of the data from the database.
- on application start it might be nice to make sure that the data is correct from the source.

#### Approach
- use content providers
- use sql databse and associated model driven interaction
- the network data returned is a JSON, so will need a JSON parser to break the data down

### Tasks
- background tasks are needed
- scheduling is not directly needed but might be a good idea to only query the data integrity funciton once a day (don't want to have it query when the application receives the onCreate call everytime).
- all asynchronous tasks (i.e. network and database queries should occur in background taks / threads).

#### Approach
- Use Async tasks that are managed by firebase job scheduling.
- Use Loaders to handle the lanuching of tasks with specific jobs.

### Lifecycle
- change in orientation, device status etc. should not change the previously selected, filled etc information.
- background tasks / threads should deliver their results to the appropriate instance and not cause performance degregation due to zombie activities.

#### Approach
- Need to overload the life-cycle methods

### UI
- RecyclerViewer grid layout is required, thus requiring an adapter and layout manager to handle this assocaited with the application.
- it would be nice that an on-click event of a poster does not switch to a new layout but blurs the background and does a new activity visibility on top (nice to have).
- overlay of a UI element to allow a user to not have to see the details to favorite / un-favorite a moive.
- selection of the non-favorite area leads to displaying the movie details

#### Approach
- use of recyclerviewer
- will need to research graphical overlays with multiple functionality
- will need to determine how to handle this for onClick events