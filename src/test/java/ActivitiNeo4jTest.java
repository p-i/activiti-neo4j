import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.task.Task;
import org.activiti.neo4j.ProcessEngineConfigurationNeo4jImpl;
import org.activiti.neo4j.ProcessEngineNeo4jImpl;
import org.activiti.neo4j.helper.BpmnParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.neo4j.test.TestGraphDatabaseFactory;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ActivitiNeo4jTest {

  protected GraphDatabaseService graphDb;
  
  protected WrappingNeoServerBootstrapper server;
  
  protected ProcessEngineNeo4jImpl processEngine;
 

  @Before
  public void setupDatabase() {

    this.graphDb = new TestGraphDatabaseFactory().newImpermanentDatabaseBuilder().newGraphDatabase();

    ProcessEngineConfigurationNeo4jImpl processEngineConfiguration = new ProcessEngineConfigurationNeo4jImpl();
    processEngineConfiguration.setGraphDatabaseService(this.graphDb);
    processEngine = (ProcessEngineNeo4jImpl) processEngineConfiguration.buildProcessEngine();
    registerShutdownHook();
  }

    @Test
    public void simpleOneTaskProcessTest() throws Exception {

        // Deploy process
        processEngine.getRepositoryService()
                .createDeployment()
                .name("expense-process.bar")
                .addClasspathResource("one-task-process.bpmn20.xml")
                .deploy();

        // Start process instance
        processEngine.getRuntimeService().startProcessInstanceByKey("oneTaskProcess");

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
  
  @Test
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


    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                cleanUp();
            }
        });
    }

    @After
    public void cleanUp() {
        this.graphDb.shutdown();

        System.out.println();
        System.out.println("Graph database shut down");
    }


}

