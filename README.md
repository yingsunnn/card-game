
# Tech Stack



# Clarification And Assumption
Since this is a homework, I can not clarily requirements with you. So, I have made some decisions based on my understanding.

1. The assignment mentioned only on REST API should be implemented. I assume this REST API contains multiple endpoints. Implementing all features with a single REST API endpoint would not comply with REST API design guidelines. GraphSQL can achieve that, but it's not a REST API. So I decided to use multiple endpoints.

2. Although the assignment mentions that the "create a deck" operation must be provided, I believe this endpoint is unnecessary because the content of a deck is fixed (52 cards with fixed face values and suits). My solution is to allow the creation of decks when creating a game, where the number of decks can be specified in the request body. Additionally, I provide another endpoint to add decks to an existing game. 

3. I assume that players can have the same name. Therefore, I won't check for duplicated requests when adding a player, as I cannot determine if it's a duplicate player.

4. Only the player themselves can see their own cards. Player can play cards only after all cards are dealed to player.

# Scalability

# How to run

# Swagger
