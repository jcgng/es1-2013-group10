# Introduction #

Description of the Use Case Specification for UC-4: AddProductToWishList.


# Details #

**Name:** AddProductToWishList

**ID:** UC-4


**Summary:**

All products must have the possibility to be added to a Wish List created by the user in the system. User can select the Wish List where he/she wants the items to be added or just add to the default one.


**Rationale:**

This is the way the system has that allows the user to save a list or multiple lists of items the user wants, providing a reminder or a quick access to those items saved to be bought later.


**Users:**

Logged user.


**Preconditions:**

A specific product page is fully loaded.


**Basic Course of Events:**

1. The user selects a specific product and gets the product page.
2. The user hits the "Add to Wish List".

Alternative Paths:

i. At step 2, the user can choose to which list, already created or a new one, wants to add the product.


**Postconditions:**

The product is added to the selected Wish List, to a new or to the default one.