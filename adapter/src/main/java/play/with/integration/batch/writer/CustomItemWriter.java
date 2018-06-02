package play.with.integration.batch.writer;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import play.with.integration.batch.model.Person;

import java.util.List;

import static java.lang.String.format;

public class CustomItemWriter implements ItemWriter<Person>, StepExecutionListener {

    long counter = 0;

    @Override
    public void write(List<? extends Person> items){
        //items.forEach(System.out::print);
        System.out.println(format("Sum of letters in each person: %s", items.stream()
                                                                    .map(Person::getName)
                                                                    .mapToInt(String::length)
                                                                    .sum()));
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution.getJobExecution().getExecutionContext().putString("JobSummary", stepExecution.getSummary());
        return stepExecution.getExitStatus();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }
}