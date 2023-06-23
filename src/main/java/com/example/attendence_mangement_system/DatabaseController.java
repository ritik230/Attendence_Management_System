package com.example.attendence_mangement_system;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_java;
import org.opencv.core.*;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;
import org.w3c.dom.Text;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.bytedeco.opencv.global.opencv_core.CV_32SC1;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.resize;


public class DatabaseController implements Initializable{

    @FXML
    //back
    private Button back;
    @FXML//student info table
    private TextField first_name, last_name,dob,email, contact_number, branch,year,adderess;
    @FXML
    private TextField management_username, management_password;
    Button management_login;
   @FXML
   private TextField  deleteid;
   Button  deletestudentbutton;
    @FXML
    private  Button photobutton;
    @FXML
    private TextField student_username, student_password;
      Button studentloginbutton;
    @FXML
    private TextField faculty_username, faculty_password;
    Button facultyloginbutton;
    @FXML
    private TextField id,updatefirst_name, updatelast_name,updatedob,updateemail, updatecontact_number,updatebranch,updateyear,updateadderess;
    Button updatestudentokbutton,updatestudentsubmibutton,stopbutton;
   static VideoCapture capture;
     private  TextField studentportalstudentname;

    private ScheduledExecutorService timer;
    FaceRecognizer r,r1;
    static HashSet <Integer> set=new HashSet<>();
    static  String temp="";
    @FXML
   private javafx.scene.text.Text idtext, studentnamestudentportal, studentidstudentportal;

    boolean  takeimage;
    String idtemp;
  static  int count=0;



   int i=100;



    static Connection connection = null;
    static PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    static int c=1;
    Alert a=new Alert(Alert.AlertType.NONE);


    public DatabaseController() throws SQLException {
        connection= Jdbc.connect();

    }
    @FXML
    protected void onbackbuttonclick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    };
    @FXML
    protected void onaddstudentbuttonclick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registrationform.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("registrationform.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
     void ondragsetid(InputEvent e)throws IOException{
        String sql = "select id from login ORDER BY id DESC LIMIT 1;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet r = preparedStatement.executeQuery();
            if(!r.next()){
                idtemp= "";
           }
          else
               idtemp = Integer.toString(Integer.valueOf(r.getString(1))+1);
        } catch (SQLException k) {
            k.printStackTrace();
        }


       idtext.setText(idtemp);

    }
    @FXML
    protected void onupdatestudentbuttonclick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("updatestudent.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("updatestudent.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    };
    @FXML
    protected void onremovestudentbuttonclick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("removestudent.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("removestudent.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    };


    public void onregistrationsubmitbuttonclick (ActionEvent e) {

       if(!takeimage){
           a.setAlertType(Alert.AlertType.WARNING);
           a.setContentText("take image first");
           a.show();
           return;
       }
       else {
           String id= idtext.getText();
           String firstname = first_name.getText().toString();
           String lastname = last_name.getText().toString();
           String dateofbirth = dob.getText().toString();
           String contactnumber = contact_number.getText().toString();
           String branchofstudy = branch.getText().toString();
           int yearofjoining = Integer.parseInt(year.getText().toString());
           String addr = adderess.getText().toString();
           String email_id = email.getText().toString();
           String sql = "insert into student_info (id,first_name,last_name,dob,ContactNumber,Email,Year,Branch) values (?,?,?,?,?,?,?,?);";
           String sql1 = "insert into login (id,username,password) values (?,?,?);";
           String sql2 = "insert into student_attendence (student_id,student_name,attendence) values (?,?,?);";

           try {
               preparedStatement = connection.prepareStatement(sql);
               preparedStatement.setString(1, id);
               preparedStatement.setString(2, firstname);
               preparedStatement.setString(3, lastname);
               preparedStatement.setString(4, dateofbirth);
               preparedStatement.setString(5, contactnumber);
               preparedStatement.setString(6, email_id);
               preparedStatement.setInt(7, yearofjoining);
               preparedStatement.setString(8, branchofstudy);
               preparedStatement.executeUpdate();
               preparedStatement=connection.prepareStatement(sql1);
               preparedStatement.setString(1,id );
               preparedStatement.setString(2, email_id);
               preparedStatement.setString(3,contactnumber );
               preparedStatement.executeUpdate();
               preparedStatement=connection.prepareStatement(sql2);
               preparedStatement.setString(1,id);
               preparedStatement.setString(2, firstname +" "+ lastname);
               preparedStatement.setInt(3,0);
               preparedStatement.execute();

               a.setAlertType(Alert.AlertType.INFORMATION);
               a.setContentText("data sumbmitted successfully !");
               a.show();




           } catch (Exception d) {
               d.printStackTrace();
           }
       }
   }

// on mangement potal login button click
    public void onmanagementloginbuttonclick(ActionEvent e) throws IOException, SQLException {
//        Window owner = management_login.getScene().getWindow();

        if (management_username.getText().isEmpty()) {

            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("Enter correct Email and Password !");
            a.show();
            return;
        }
      else if(Jdbc.validate(management_username.getText(),management_password.getText())){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("managementportal.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("managementportal.fxml"));
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
      else{
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("Entered Email and Password is wrong !");
            a.show();
        }
    }
    // student login button click

    @FXML
    public void onstudentloginbuttonclick(ActionEvent e) throws IOException, SQLException {
//        Window owner = management_login.getScene().getWindow();
         temp= student_username.getText();
        if (student_username.getText().isEmpty()) {
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("Enter correct Email and Password !");
            a.show();
            return;
        }
        else if(Jdbc.validate(student_username.getText(),student_password.getText())){

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("studentportal.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("studentportal.fxml"));
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        else{
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("Entered Email and Password is wrong !");
            a.show();
        }
    }

    public  void  onshowattendencebuttonclick(ActionEvent e)throws SQLException {

    String sql ="select * from login where username=? ;";
    String percentage= "";
    String id="";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, temp);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            id= rs.getString(1);
            System.out.print(temp);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        String sql1 ="select attendence from student_attendence where student_id=? ;";
        try {
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            percentage= rs.getString(1);

            System.out.print(temp);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText("your total attendence is  =>" + (Integer.valueOf(percentage)*100/20) + "%");
        a.show();

    }
    public void onmousemoveinstudentportal(InputEvent e){
        if(count!=0)return;
        count++;
        String sql1 ="select * from student_info where Email=? ;";
        String name="";
        String id="";
        try {
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1, temp);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
           id = rs.getString(1);
           name=  rs.getString(2);

            System.out.print(temp);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         studentnamestudentportal.setText(name);
         studentidstudentportal.setText(id);
    }

    public void onfacultyloginbuttonclick(ActionEvent e) throws IOException, SQLException {
//        Window owner = management_login.getScene().getWindow();

        if (faculty_username.getText().isEmpty()) {

            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("Enter correct Email and Password !");
            a.show();
            return;
        }
        else if(Jdbc.validate(faculty_username.getText(),faculty_password.getText())){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("facultyportal.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("facultyportal.fxml"));
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else{
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("Entered Email and Password is wrong !");
            a.show();
        }
    }

    public void onupdatestudentokbuttonclick (ActionEvent e) {


        String id1 = id.getText().toString();
        String sql = "select * from student_info student_info where id=(?)";


        try {
            preparedStatement  = connection.prepareStatement(sql);
            preparedStatement.setString(1,id1);

            ResultSet rs=preparedStatement.executeQuery();
            if(!rs.next())
            { a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("Entered id is wrong !");
            a.show();}
            else{
             updatefirst_name.setText(rs.getString("first_name"));
             updatelast_name.setText(rs.getString("last_name"));
             updatedob.setText(rs.getString("dob"));
             updateemail.setText(rs.getString("email"));
             updatecontact_number.setText(rs.getString("contactNumber"));
             updatebranch.setText(rs.getString("branch"));
             updateyear.setText(rs.getString("year"));

            }

        } catch (Exception d) {
            d.printStackTrace();
        }

    }
//    on delete button click
    public void ondeletestudentbuttonclick (ActionEvent e) throws SQLException {

        String id1 = deleteid.getText().toString();
        String sql = "delete from student_info where id=(?);";
//        String id1 = id.getText().toString();
        String sql1 = "select * from student_info student_info where id=(?)";
        String sql2 = "delete from student_attendence where student_id=(?);";
        String sql3 = "delete from login where id=(?);";



            preparedStatement  = connection.prepareStatement(sql1);
            preparedStatement.setString(1,id1);

            ResultSet rs=preparedStatement.executeQuery();
            if(!rs.next())
            { a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("Entered id is wrong !");
                a.show();}
            else
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id1);
            a.setAlertType(Alert.AlertType.CONFIRMATION);
            a.setContentText("you want to delete data of student id -> "+ id1);


             a.show();
             preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.setString(1, id1);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sql3);
            preparedStatement.setString(1, id1);
            preparedStatement.executeUpdate();


        }
        catch (Exception k){
            k.printStackTrace();
        }
    };
        // on update submit button click
        public void onstudenupdatesubmitbuttonclick (ActionEvent e){


            String firstname = updatefirst_name.getText().toString();
            String lastname = updatelast_name.getText().toString();
            String dateofbirth = updatedob.getText().toString();
            String contactnumber = updatecontact_number.getText().toString();
            String branchofstudy = updatebranch.getText().toString();
            int yearofjoining = Integer.parseInt(updateyear.getText().toString());
            String addr = updateadderess.getText().toString();
            String email_id = updateemail.getText().toString();
            String id1 = id.getText().toString();
            String sql = "update  student_info set first_name=?,last_name=?,dob=?,ContactNumber=?,Email=?,Year=?,Branch=? where id=?;";



            try {
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, firstname);
                preparedStatement.setString(2, lastname);
                preparedStatement.setString(3, dateofbirth);
                preparedStatement.setString(4, contactnumber);
                preparedStatement.setString(5, email_id);
                preparedStatement.setInt(6, yearofjoining);
                preparedStatement.setString(7, branchofstudy);
                preparedStatement.setString(8, id1);

                preparedStatement.executeUpdate();
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("student data updated successfully!");
                a.show();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("updatestudent.fxml"));
                Parent root = FXMLLoader.load(getClass().getResource("updatestudent.fxml"));
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();


            } catch (Exception d) {
                d.printStackTrace();
            }

        }

@FXML
    public void ontakeimagebuttonclick (ActionEvent event) throws ClassNotFoundException {
  // loading opencv
    takeimage=true;
    Loader.load(opencv_java.class);
    ImageView imageView = new ImageView();
    capture = new VideoCapture(0);
    Stage stage = new Stage();
    HBox hbox = new HBox(imageView);
    Scene scene = new Scene(hbox);
    Runnable frameGrabber = new Runnable() {
        @Override
        public void run() {
            System.out.print(i);
            if(i==500){
            imageView.setImage(null);
            stage.close();

            }
            imageView.setImage(new Image(press(capture)));
        }
    };

    this.timer = Executors.newSingleThreadScheduledExecutor();
    this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
    stage.setScene(scene);
    stage.show();

    }

    public  void onstopbuttonclick(){
           capture.release();
           trainimage();
    }

 public InputStream press(VideoCapture capture) {
            Mat mat = new Mat();

     capture.read(mat);

     MatOfByte bytes = new MatOfByte();
     MatOfRect facesDetected = new MatOfRect();
     CascadeClassifier cascadeClassifier = new CascadeClassifier();
     int minFaceSize = Math.round(mat.rows() * 0.1f);


     cascadeClassifier.load("./src/main/resources/Haar/haarcascade_frontalface_alt.xml");
     cascadeClassifier.detectMultiScale(mat,
             facesDetected,
             1.1,
             3,
             Objdetect.CASCADE_SCALE_IMAGE,
             new Size(minFaceSize, minFaceSize),
             new Size()
     );
     System.out.print("faces detected");
     Rect[] facesArray = facesDetected.toArray();
     String name= first_name.getText();
     String idt= idtext.getText();
     for(Rect face : facesArray) {
         Imgproc.rectangle(mat, face.tl(), face.br(), new Scalar(0, 0, 255), 3);
         Rect rectCrop = new Rect(face.x, face.y, face.width, face.height);
         Mat dst = new Mat();
         Mat image_roi = new Mat(mat,rectCrop);
         Imgproc.cvtColor(image_roi, dst, Imgproc.COLOR_RGB2GRAY);
         int label[]= new int[1];
         double[] confidence= new double[1];
        imwrite("dataset/"+name+"."+ idt +"." + Integer.toString(i) +".jpg",dst);
        i++;
     }

     Imgcodecs.imencode(".jpg", mat, bytes);

           if(i==500){
               onstopbuttonclick();
           }


     InputStream inputStream = new ByteArrayInputStream(bytes.toArray());
    return  inputStream;
 }

 // training image
public static void trainimage() {



    String trainingDir = "dataset" ;
//        Mat testImage = imread("", CV_LOAD_IMAGE_GRAYSCALE);
    File root = new File(trainingDir);
    System.out.print("");
    FilenameFilter imgFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            name = name.toLowerCase();
            return name.endsWith(".jpg") || name.endsWith(".png");
        }
    };
    File[] imageFiles = root.listFiles(imgFilter);
    Vector<Mat> images = new Vector(imageFiles.length);

    Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
    labels.create(imageFiles.length,1,CV_32SC1);
    int counter = 0;
    for(File image : imageFiles){
        Mat img = imread(image.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
        resize(img, img, new Size(101, 101));
        String[] label = image.getName().split("\\.");
        int j= Integer.parseInt(label[1]);
        System.out.println(label[0]);
        System.out.print(label[1]);
        System.out.print(label[2]);
        images.add(counter, img);

        labels.put(counter,0,j);

        counter++;
    }

    FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();
    faceRecognizer.train(images, labels);
    faceRecognizer.save("model"+"/"+trainingDir+"classifierLBPH.yml");
    System.out.println("Training Done!!!");

//
}
@FXML

    public void ontakeattendencebuttonclick (ActionEvent event) throws ClassNotFoundException {

    Loader.load(opencv_java.class);
    System.out.println("ontakeimage");
    ImageView imageView = new ImageView();
    capture = new VideoCapture(0);
    Button b= new Button();
    r= LBPHFaceRecognizer.create();
    System.out.println("ontakeimage");
    r.read("model/datasetclassifierLBPH.yml");
    Stage stage = new Stage();
    HBox hbox = new HBox(imageView);
    Scene scene = new Scene(hbox);

    Runnable frameGrabber = new Runnable() {
        @Override
        public void run() {
            try {
                imageView.setImage(new Image(recognize(capture)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    this.timer = Executors.newSingleThreadScheduledExecutor();
    this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
    stage.setScene(scene);
    stage.show();

    }

 public InputStream recognize(VideoCapture capture) throws SQLException {
     String trainingDir = "dataset";
     CascadeClassifier cascadeClassifier = new CascadeClassifier();
     cascadeClassifier.load("./src/main/resources/Haar/haarcascade_frontalface_alt.xml");
     Mat mat = new Mat();
     capture.read(mat);
     MatOfRect facesDetected = new MatOfRect();
     HashMap<Integer, String> map = new HashMap<>();

     int minFaceSize = Math.round(mat.rows() * 0.1f);
     cascadeClassifier.detectMultiScale(mat,
             facesDetected,
             1.1,
             3,
             Objdetect.CASCADE_SCALE_IMAGE,
             new Size(minFaceSize, minFaceSize),
             new Size()
     );
     Rect[] facesArray = facesDetected.toArray();

     for (Rect face : facesArray) {

         Imgproc.rectangle(mat, face.tl(), face.br(), new Scalar(0, 0, 255), 3);
         Rect rectCrop = new Rect(face.x, face.y, face.width, face.height);
         Mat dst = new Mat();
         Mat image_roi = new Mat(mat, rectCrop);
         Imgproc.cvtColor(image_roi, dst, Imgproc.COLOR_RGB2GRAY);
         int label[] = new int[1];
         double[] confidence = new double[1];
         r.predict(dst, label, confidence);

         int j = r.predict_label(dst);
         System.out.print(confidence[0]);

         String name = "";

         if (confidence[0] > 75) {
             j = -1;
         } else {
             set.add(j);
             if (!map.containsKey(j)) {
                 String sql1 = "select first_name from student_info where id=? ;";
                 try {
                     preparedStatement = connection.prepareStatement(sql1);
                     preparedStatement.setString(1, Integer.toString(j));
                     ResultSet rs = preparedStatement.executeQuery();
                     rs.next();
                     name = rs.getString(1);
                     map.put(j, name);

                     System.out.print(temp);
                 } catch (SQLException ex) {

                 }}}

             Imgproc.putText(mat, map.get(j)  + " "+j, face.br(), 1, 5.0, new Scalar(256, 256, 256));

         }
         MatOfByte bytes = new MatOfByte();
         Imgcodecs.imencode(".jpg", mat, bytes);
         i++;
//  System.out.print("aaa rha yha");
         if (i == 2000)
             mark();


         InputStream inputStream = new ByteArrayInputStream(bytes.toArray());

         return inputStream;
     }

  public static void mark() throws SQLException{
            capture.release();
            System.out.print(set.size());
      String sql=    "UPDATE student_attendence SET attendence= attendence + 1 WHERE student_id = ?";
            for(int i: set){

                try {
                    System.out.print("try m aa rha");
                    preparedStatement = connection.prepareStatement(sql);
                    System.out.print("connected");
                    preparedStatement.setString(1, Integer.toString(i));
                    System.out.print("connected");
                    preparedStatement.executeUpdate();
                    System.out.print("query executed");
                }
                catch (Exception e){
                    System.out.print(e);

                }
            }
  }

    public static void saveImage(Mat imageMatrix, String targetPath) {
        Imgcodecs imgcodecs = new Imgcodecs();
        imwrite(targetPath, imageMatrix);
    }
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}

