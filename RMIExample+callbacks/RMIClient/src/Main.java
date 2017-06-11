import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import bozek.mateusz.common.domain.MessageEvent;
import bozek.mateusz.common.domain.User;
import bozek.mateusz.common.features.RMICrawlerProxy;
import javafx.application.Application;

public class Main extends Application
{
	
	
	public static void main( String[] args ) throws NamingException, NotBoundException, IOException
	{
		launch(args);
	}
	
	private static class CustomMessageEvent extends UnicastRemoteObject implements MessageEvent
	{
		private static final long serialVersionUID = 6738492455439057283L;
		
		protected CustomMessageEvent() throws RemoteException
		{
			super();
		}
		
		@Override
		public void messageSended( String message )
		{
			System.out.printf( "Server message: %s\n", message );
		}
	}

	
	 @Override
	    public void start(Stage stage) throws Exception {

		 	//configuration server RMI 
		  	File file = new File("students.txt");
	        String host = "localhost";
			int port = 5060;
			String name = "rmi://" + port + "/crawler";

	        Registry registry = LocateRegistry.getRegistry( host, port );
	        RMICrawlerProxy crawlerServer = (RMICrawlerProxy) registry.lookup( name );
		 
		 
	        StackPane stackPane = new StackPane();

	        Group root = new Group();
	        stage.setScene(new Scene(root));

	        VBox vbox = new VBox(0);
	        vbox.setPadding(new Insets(0));
	//----------------------------------------------------------------------------------------
	        MenuBar mbar = new MenuBar();
	        mbar.prefWidthProperty().bind(stage.widthProperty());

	        Menu program = new Menu("Program");
	        mbar.getMenus().add(program);


	        MenuItem close = new MenuItem("Close");
	        close.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                stage.close();
	                try {
						crawlerServer.logoutUser();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        });
	        close.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));

	        program.getItems().add(close);

	        Menu about = new Menu("About");
	        mbar.getMenus().add(about);

	        MenuItem CommunicationAbout = new MenuItem("About");
	        about.setOnAction((ActionEvent event) -> {
	            Label secondLabel = new Label("Example program information.\n\nAuthor : Mateusz Bozek");
	            StackPane secondaryLayout = new StackPane();
	            secondaryLayout.getChildren().addAll(secondLabel);
	            Scene secondScene = new Scene(secondaryLayout, 230, 100);
	            Stage secondStage = new Stage();
	            secondStage.setTitle("About");
	            secondStage.setScene(secondScene);
	            secondStage.show();
	        });

	        about.getItems().add(CommunicationAbout);
	//-------------------------------------------------------------------------

	        Tab student = new Tab();
	        student.setText("Student");
	        student.setClosable(false);

	        TabPane tabPane = new TabPane();
	        tabPane.prefWidthProperty().bind(stage.widthProperty());

	      
	        List<bozek.mateusz.common.domain.Student> studentFile = crawlerServer.getAllStudents();


	        TableView<bozek.mateusz.common.domain.Student> studentsTable = new TableView<>();


	        TableColumn mark = new TableColumn("Mark");
	        mark.setCellValueFactory(new PropertyValueFactory<>("mark"));

	        TableColumn firstNameCol = new TableColumn("First Name");
	        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

	        TableColumn lastNameCol = new TableColumn("Last Name");
	        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

	        TableColumn age = new TableColumn("Age");
	        age.setCellValueFactory(new PropertyValueFactory<>("age"));


	        studentsTable.getColumns().addAll(mark, firstNameCol, lastNameCol, age);

	        ObservableList<bozek.mateusz.common.domain.Student> studentsList = FXCollections.observableArrayList();

	        studentFile.forEach(s -> {
	            studentsList.add(s);
	        });


	        studentsTable.setItems(studentsList);

	        student.setContent(studentsTable);

	//------------------------------------------------------------------------------------

	        Tab log = new Tab();
	        log.setText("Log");
	        log.setClosable(false);

	        TableView<String> logTable = new TableView<>();

	        List<String> logResponse = crawlerServer.getLogs();

	        logResponse.forEach(s -> System.out.println(s));

	        ObservableList<String> items = FXCollections.observableArrayList();

	        logResponse.forEach(s -> {
	            items.add(s);
	        });

	        ListView<String> list = new ListView<>();
	        list.setItems(items);
//	        logTable.setItems(studentsLog);
	        log.setContent(list);

	        //_____________________________________________________________________________________________________________

	        Tab histogram = new Tab();
	        histogram.setText("Histogram");
	        histogram.setClosable(false);


	        tabPane.getTabs().addAll(student, log, histogram);

	        HBox mainPanel = new HBox(0);
	        HBox secondPanel = new HBox(0);

	        mainPanel.getChildren().add(mbar);
	        secondPanel.getChildren().add(tabPane);

	        vbox.getChildren().add(mainPanel);
	        vbox.getChildren().add(secondPanel);

	        Map<Double, Integer> mapRatingStudent = ratingStudent(studentFile);

	        Scene scene = new Scene(vbox, 450, 550); // scena
	        setBarTab(histogram, mapRatingStudent);
	        stage.setScene(scene);
	        stage.show();

	    }

	    private void setBarTab(Tab barTab, Map<Double, Integer> listRatingStudents) {
	        String rating2 = "2";
	        String rating3 = "3";
	        String rating35 = "3.5";
	        String rating4 = "4";
	        String rating45 = "4.5";
	        String rating5 = "5";
	        final CategoryAxis xAxis = new CategoryAxis();
	        final NumberAxis yAxis = new NumberAxis();
	        final BarChart<String, Number> bc =
	                new BarChart<String, Number>(xAxis, yAxis);
	        bc.setTitle("Distribution of marks");
	        xAxis.setLabel("Mark");
	        yAxis.setLabel("Count");

	        System.out.println("Ocena; "+listRatingStudents.get(2.0));

	        XYChart.Series series3 = new XYChart.Series();
	        series3.setName("Marks");
	        series3.getData().add(new XYChart.Data(rating2, listRatingStudents.get(2.0)));
	        series3.getData().add(new XYChart.Data(rating3, listRatingStudents.get(3.0)));
	        series3.getData().add(new XYChart.Data(rating35, listRatingStudents.get(3.5)));
	        series3.getData().add(new XYChart.Data(rating4, listRatingStudents.get(4.0)));
	        series3.getData().add(new XYChart.Data(rating45, listRatingStudents.get(4.5)));
	        series3.getData().add(new XYChart.Data(rating5, listRatingStudents.get(5.0)));

	        barTab.setText("Histogram");
	        bc.getData().addAll(series3);
	        barTab.setContent(bc);
	    }


	    public Map<Double, Integer> ratingStudent(List<bozek.mateusz.common.domain.Student> list) {

	        double[] ratingTab = {2, 3, 3.5, 4, 4.5, 5};

	        Map<Double, Integer> ratingStudent = new HashMap<>();

	        for(int i = 0; i < ratingTab.length; i++){

	            int counter = 0;

	            for(int j = 0; j < list.size(); j++){

	                if(ratingTab[i] == list.get(j).getMark()){
	                    counter++;
	                }
	            }
	            ratingStudent.put(ratingTab[i], counter);
	        }

	       return ratingStudent;
	    }

	    private class MyMenuHandler implements EventHandler<ActionEvent> {

	        @Override
	        public void handle(ActionEvent event) {
	            doShowMessageDialog(event);
	        }

	        private void doShowMessageDialog(ActionEvent event) {

	            MenuItem mi = (MenuItem) event.getSource();
	            String item = mi.getText();
	            Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setTitle("Information dialog");
	            alert.setHeaderText("Menu item selection information");
	            alert.setContentText(item + " menu item selected");

	            alert.showAndWait();
	        }

	    }
	
}
