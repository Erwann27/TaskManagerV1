package toDoList;

import model.toDoList.ComplexTask;
import model.toDoList.Priority;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ComplexTaskUnitTest {

    @Test
    public void shouldCreateComplexTask() {
        // GIVEN
        String desc = "TEST";
        Priority priority = Priority.MINOR;
        ComplexTask task;
        // WHEN
        task = new ComplexTask(desc, priority);
        // THEN
        assertThat(task).isNotNull();
        assertThat(task.getDescription()).isEqualTo(desc);
        assertThat(task.getPriority()).isEqualTo(priority);
    }

    @Test
    public void shouldAddSubtask() {
        // GIVEN
        String desc = "TEST";
        Priority priority = Priority.MINOR;
        ComplexTask task = new ComplexTask(desc, priority);
        // WHEN
        task.addSubTask(task);
        // THEN
        assertThat(task.getSubTasks()).contains(task);
    }

}