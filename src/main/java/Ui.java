public class Ui {

    public void getJustBotExceptionMessage(JustbotException e) {
        System.out.println("------------------------------------------");
        System.out.println(e.getMessage());
        System.out.println("------------------------------------------");
    }
    public void botIntro(String botName) {
        System.out.println("------------------------------------------");
        System.out.println("Hello I'm " + botName);
        System.out.println("What can I do for you?");
        System.out.println("------------------------------------------");
    }

    public void listMessage(TaskList taskList) {
        System.out.println("------------------------------------------");
        System.out.println("Here are the tasks in your list:\n");
        for(int i =0; i < taskList.size(); i++) {
            int taskListCount = i + 1;
            Task currTask = taskList.get(i);
            System.out.print(taskListCount + ". " + currTask.toString() + "\n");
        }
        System.out.println("------------------------------------------");
    }

    public void markMessage(TaskList taskList, int taskNumber) {
        int taskIndex = taskNumber - 1;
        Task currTask = taskList.get(taskIndex);
        System.out.println("------------------------------------------");
        System.out.println("Nice! I have marked this task as done:\n" + currTask.toString());
        System.out.println("------------------------------------------");
    }

    public void unmarkMessage(TaskList taskList, int taskNumber){
        int taskIndex = taskNumber - 1;
        Task currTask = taskList.get(taskIndex);
        System.out.println("------------------------------------------");
        System.out.println("OK, I've marked this task as not done yet:\n" + currTask.toString());
        System.out.println("------------------------------------------");
    }

    public void addTaskMessage(TaskList taskList, Task task) {
        System.out.println("------------------------------------------");
        int numberOfTasks = taskList.size();
        System.out.println( "Got it. I've added this task:\n" + task.toString() + "\n" + "Now you have " + numberOfTasks + " tasks in your list.");
        System.out.println("------------------------------------------");
    }

    public void deleteTaskMessage(TaskList taskList, int taskNumber) {
        System.out.println("------------------------------------------");
        int taskIndex = taskNumber -1;
        Task currTask = taskList.get(taskIndex);
        int numberOfTasks = taskList.size() - 1;
        System.out.println( "Noted. I've removed this task:\n" + currTask.toString() + "\n" + "Now you have " + numberOfTasks + " tasks in your list.");
        System.out.println("------------------------------------------");
    }

    public void byeMessage() {
        System.out.println("------------------------------------------");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("------------------------------------------");
    }
}
