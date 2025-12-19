# Project - Capstone 3 - Ecommerce Videogame Store - Owner: jpalmgren8
A website on localhost that simulates an ecommerce website for videogames. 

## Description - What is the program?
This application works as a website on localhost that acts as a videogame store front. It works with a java backend, database connection in MySql, and HTML/CSS page.
Currently, the program allows for a search of products within a price range, login, create new account, and admins can interact with the database to perform CRUD operations.

## Main Features - Functions of the program
* Display a web-store like interface.
* Website connects to a database for information.
* User can see products and search via price.
* Showcase bug fixes and features.
  
## Program flow and navigation
* First the html file must be opened.
* Then the user can run the application.
* Once the application boots up, open the browser window.
* The website should load via localhost.
* The user should see all items load by default on screen.
* They can sort products using price minimum and maximum sliders.
* The user can also sort by specific category to refine the search further.

## Testing process
* I tested the program and database using Insomnia. Using 12 pre-made tests to see if the website would work behind the scenes.
* After tinkering, I passed 12/12 tests.
* The hardest tests to figure out was the CRUD operations. 

## Known problems and incomplete areas
* Shopping cart, checkout, profile features are incomplete.
* Product search via slider doesn't work when both sliders are set to 0. It displays all items.
* Running Insomnia tests twice in a row makes faulty database data, causing the tests to fail.
* Duplicate controller bug still occurs on the store screen sometimes when sorting by accessories category.

## Future improvements and features
* Implement the shopping cart, checkout, and profile features.
* Fix few remaining bugs with searching.
* Clean up program to be more consistent with other modules in it.
* Add more data to the database for the store front to use.

## Interesting piece of code
@PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> addCategory(@RequestBody Category category)
    {
        try
        {
            Category created = categoryDao.create(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

I find this snippet of code interesting because I spent a lot of time debugging the CRUD operation methods.
Spending the most time working on this addCategory method and how it interacts with the rest of the program.
It took a while to understand that I needed http responses and had to use response entities. 
