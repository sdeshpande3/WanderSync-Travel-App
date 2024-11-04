Arav Arora - bloating + long method

So, first there was one Code Smells issue that connected with bloating. There was an example of bloating in the calculateTotalPrice() method. It has grown so large that we think it can’t be effectively handled. Since it’s got to deal with many things such as taxable item or other. As there’s taxable information or reducing the price, that would be necessary. We should help split it up into a great deal of smaller modules. By splitting it up into smaller modules/functions, you’re able to have greater functionality through the code. 


Vasili Fovos- Switch statement in calculateTotalPrice()

One noticeable code smell in this file is the use of a switch statement within the calculateTotalPrice()  method to handle discounts. This violates the Open/Closed Principle of SOLID by making it difficult to add new discount types without modifying this method. Additionally, this logic clutters the order class, making it less cohesive. We can refactor this by moving discount calculations into each Item class and implementing a DiscountStrategy interface that each discount type can extend which makes it scalable for all types of discounts.


Moksh Shah - Large Class

Here is another example of bloating. In large class, the issue is that one class has many fields, methods, and lines of code. When it has many lines of code, it can be difficult for the programmer to make modifications in the future. I see an example of large class with the Order class, for it seems to have too many responsibilities. As it has too many responsibilities, it might be better to potentially extract the class a bit. What we did was that we created a new class called OrderPrinter. The OrderPrinter class helped us remove the amount of Code Smells in this project. This is because we were able to slowly abstract part of the work of the class. This allowed us to 


Samarth Tewari - Long method

I fixed the fact that the method of calculating total price was performing too many things in its logic. I opted to break it down into multiple smaller methods which were “calculate tax”, “apply gift card discount”, “apply large order discount” to name a few. This overall helps make the entire codebase more readable as you do not have to examine long method implementations to find out what is doing what in the code base. Breaking it down into smaller and smaller methods allows you to read the logic that is occurring without that much thinking required.


Sammit Deshpande - Long method

I identified a long method issue in the sendConfirmationEmail() function, where the logic for constructing the email message and sending it was overly complex and combined multiple responsibilities. This made it harder to read and maintain, as the method had to handle both the creation of the message and the email-sending functionality. To address this, I removed the construction logic into a new method called createOrderMessage(). This separation improves the overall readability of the code, making it easier to understand and maintain while improving the clarity of each method’s responsibility. By breaking the long method into smaller, more focused methods, it will have better organization and maintainability.


Tucker Ritti - Magic Numbers

I identified and removed magic numbers from the Order class to enhance clarity and maintainability. I replaced the value 10.0, which represented the gift card discount, with a constant GIFT_CARD_DISCOUNT. This change was made in the reduceGiftCardPrice and applyGiftCardDiscount methods. By using a constant, the code becomes more self-explanatory, and any adjustments to the discount value can be made in one place, reducing errors and improving overall readability.
