SOLID/GRASP Assignment – Team 40
Members: Sammit Deshpande, Arav Arora, Vasili Fovos, Samarth Tewari, Tucker Ritti, Moksh Shah



1.	Single Responsibility Principle (SOLID)

One principle implemented in our codebase, especially through the Task class(and necessary subclass) is the single responsibility principle. Through the use of the subclasses, it is clear that each class has its own responsibility for dealing with the Tasks. Each class has its own single responsibility for dealing with the Task. Each complete task gives them the one responsibility they’re doing. As seen with the RecurringTask Class, all of the methods have high cohesion, because they’re helping with the single responsibility. 


2.	Open/Closed Principle (SOLID)

As you can see, using the abstract class, we have made it so that it follows the Open/Closed principle. It follows the open/closed principle because we have the Task Class. The Task class is open for extension but closed for modification. Through the Task class(Abstract class), we have subclasses that implement the specific abilities/behaviors of the program. For example, the RecurringTaskClass deals specifically with the implementation of tasks that have recurrent tasks. The HighPriorityTask class helps with tasks that are more important to complete. By the open/closed principles, this ensures that we aren’t directly modifying our modules. 


3.	Dependency Inversion Principle (SOLID)

In our codebase, the ProjectManager and Project classes interact with tasks through abstractions. They interact with the abstract Task class instead of specific task types like RecurringTask or HighPriorityTask. This ensures that higher-level modules are not tightly coupled to lower-level modules, promoting flexibility. If a new type of task needs to be introduced, it can be done without modifying the ProjectManager class, following the dependency inversion principle.


4.	Low Coupling (GRASP)

Our system promotes low coupling by minimizing the dependencies between different components. The Project class interacts with the Task abstraction rather than specific implementations like RecurringTask or HighPriorityTask. This decouples the Project class from the details of individual task types, allowing for changes in one class without requiring changes in the other. By minimizing dependencies, we make the system easier to maintain, test, and extend.


5.	Controller (GRASP)

The Controller pattern is applied in the ProjectManager class, which acts as the controller for managing the overall project and its associated tasks. The ProjectManager class takes input from the user or other parts of the system and manages the behavior of multiple classes, including adding/removing tasks from projects and assigning team members. The ProjectManager serves as a central point of control, following the Controller principle in GRASP.


6.	Creator (GRASP)

Following the Creator pattern, the Project class is responsible for creating Task objects. Since the project class contains all the relevant information about the tasks such as start/end dates and project requirements, it fits the role of creator in our system. By handling the creation of tasks within the Project class, we ensure that the project maintains control over how its tasks are initialized. This also makes the app more modular, as task creation is handled separate from other functions.
