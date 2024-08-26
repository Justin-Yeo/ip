public class Deadline extends Task {
    protected String by;
    public Deadline(String taskDescription, String by) {
        super(taskDescription);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by:" + this.by + ")";
    }

    @Override
    public String getTaskType() {
        return "Deadline";
    }

    @Override
    public String getTimeConstraint() {
        return "by: " + this.by;
    }
}
