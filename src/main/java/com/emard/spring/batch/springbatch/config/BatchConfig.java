package com.emard.spring.batch.springbatch.config;

import javax.sql.DataSource;

import com.emard.spring.batch.springbatch.listener.JobCompletionNotificationListener;
import com.emard.spring.batch.springbatch.listener.PersonItemProcessor;
import com.emard.spring.batch.springbatch.model.Person;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * BatchConfig
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private  final JobBuilderFactory jobBuilderFactory;
    private  final StepBuilderFactory stepBuiderFactory;
    private  final DataSource dataSource;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuiderFactory, DataSource dataSource){
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuiderFactory = stepBuiderFactory;
        this.dataSource = dataSource;
    }

    @Bean
    public FlatFileItemReader<Person> reader(){
        FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
        reader.setResource(new ClassPathResource("persons.csv"));
        reader.setLineMapper(new DefaultLineMapper<Person>() {{
            setLineTokenizer(new DelimitedLineTokenizer(){{
                setStrict(false);//si le nb de colonnes n'est pas conforme il laisse passer aussi
                setNames(new String[] {"firsName", "lastName", "email","age"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>(){{
                setTargetType(Person.class);
            }});
        }});
        return reader;
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();  
    }

    @Bean
    public JdbcBatchItemWriter<Person> writer() {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
        writer.setSql("INSERT INTO person(first_name,last_name,email,age) VALUES (:firstName, :lastName, :email, :age)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuiderFactory.get("step1")
                .<Person, Person> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    
}