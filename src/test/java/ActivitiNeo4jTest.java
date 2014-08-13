import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.support.node.Neo4jHelper;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(locations = "classpath:/applicationContextNeoTest.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback=false)
public class ActivitiNeo4jTest {

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RuntimeService runtimeService;

    @Rollback(false)
    @Before
    public void cleanUpGraph() {
      //Neo4jHelper.cleanDb(template);
    }

    @Test
    @Transactional
    public void simpleOneTaskProcessTest() throws Exception {

        // Deploy process
        processEngine.getRepositoryService()
                .createDeployment()
                .name("expense-process.bar")
                .addClasspathResource("one-task-process.bpmn20.xml")
                .deploy();


        // Start process instance
        processEngine.getRuntimeService().startProcessInstanceByKey("oneTaskProcess");

        if (true) return;

        // See if there is a task for kermit
        List<Task> tasks = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee("kermit")
                .list();
        assertEquals(1, tasks.size());

        Task task = tasks.get(0);
        assertEquals("My task", task.getName());

        // Complete task
        processEngine.getTaskService().complete(task.getId());

    }
  
//  @Test
//  public void startALotOfProcesses() throws Exception {
//
//    // Deploy process
//    InputStream inputStream = this.getClass().getResourceAsStream("one-task-process.bpmn20.xml");
//    processEngine.getRepositoryService().deploy(inputStream);
//    
//    // Start a few  process instances
//    int nrOfInstances = 10000;
//    long start = System.currentTimeMillis();
//    for (int i=0; i<nrOfInstances; i++) {
//      processEngine.getRuntimeService().startProcessInstanceByKey("oneTaskProcess");
//    }
//    long end = System.currentTimeMillis();
//    System.out.println("Took " + (end-start) + " ms");
//    
//    // See if there are tasks for kermit
//    List<Task> tasks = processEngine.getTaskService().findTasksFor("kermit");
//    System.out.println("Got " + tasks.size() + " tasks");
//    assertEquals(nrOfInstances, tasks.size());
//    
//  }

    @Ignore
  @Test
  @Transactional
  public void parallelTest() throws Exception {
    // Deploy process

      //InputStream inputStream = this.getClass().getResourceAsStream("parallel-process.bpmn");
    //ProcessDefinition processDefinition = processEngine.getRepositoryService().deploy(inputStream);

      processEngine.getRepositoryService()
              .createDeployment()
              .name("expense-process.bar")
              .addClasspathResource("parallel-process.bpmn")
              .deploy();
    
    // Start process instance
    processEngine.getRuntimeService().startProcessInstanceByKey("parallelProcess");
    
    // two task should now be available for kermit
    //List<Task> tasks = processEngine.getTaskService().findTasksFor("kermit");
    List<Task> tasks = processEngine.getTaskService()
              .createTaskQuery()
              .taskAssignee("kermit")
              .list();

    assertEquals(2, tasks.size());
    
    boolean foundTask1 = false;
    boolean foundTask2 = false;
    for (Task task : tasks) {
      if (task.getName().equals("myTask1")) {
        foundTask1 = true;
      } else if (task.getName().equals("myTask2")) {
        foundTask2 = true;
      }
    }
    assertTrue(foundTask1 && foundTask2);
  }

    @Ignore
  @Test
  public void startMultipleProcessInstancesTest() throws Exception {

    // Deploy process
//    InputStream inputStream = this.getClass().getResourceAsStream("one-task-process.bpmn20.xml");
//    ProcessDefinition processDefinition = processEngine.getRepositoryService().deploy(inputStream);

      processEngine.getRepositoryService()
              .createDeployment()
              .name("expense-process.bar")
              .addClasspathResource("one-task-process.bpmn20.xml")
              .deploy();
    
    // Start a few  process instances
    for (int i=0; i<20; i++) {
      processEngine.getRuntimeService().startProcessInstanceByKey("oneTaskProcess");
    }
    
    // See if there are tasks for kermit
    //List<Task> tasks = processEngine.getTaskService().findTasksFor("kermit");
      List<Task> tasks = processEngine.getTaskService()
              .createTaskQuery()
              .taskAssignee("kermit")
              .list();
    assertEquals(20, tasks.size());
    
  }
//  
//  @Test
//  public void delegateCallAndNoStackOverflowTest() {
//    // Deploy process
//    InputStream inputStream = this.getClass().getResourceAsStream("customJavaLogic.bpmn");
//    ProcessDefinition processDefinition = processEngine.getRepositoryService().deploy(inputStream);
//    
//    // Start process instance
//    processEngine.getRuntimeService().startProcessInstanceByKey("customJavaLogic");
//  }


}

