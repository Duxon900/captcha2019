package captcha2019.control.ui;

import captcha2019.Captcha;
import captcha2019.control.db.DBKudeatzaile;
import captcha2019.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.sqlite.core.DB;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class MainUIKud implements Initializable {

    @FXML
    private TableView<Captcha> taula;

    @FXML
    private TableColumn<?, ?> zutId;

    @FXML
    private TableColumn<?, ?> zutPath;

    @FXML
    private TableColumn<Captcha, String> zutContent;

    @FXML
    private TableColumn<?, ?> zutDate;

    @FXML
    private TableColumn<Captcha, Image> zutIrudia;

    ObservableList<Captcha> emaitza= FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ResultSet resultSet= DBKudeatzaile.getInstantzia().execSQL("select * from captchas");

        try {
            datuakSartu(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        zutId.setCellValueFactory(new PropertyValueFactory<>("id"));
        zutPath.setCellValueFactory(new PropertyValueFactory<>("filename"));
        zutContent.setCellValueFactory(new PropertyValueFactory<Captcha, String>("value"));
        zutDate.setCellValueFactory(new PropertyValueFactory<>("data"));
        zutIrudia.setCellValueFactory(new PropertyValueFactory<>("irudia"));

        zutIrudia.setCellFactory(p -> new TableCell<>() {
            public void updateItem(Image image, boolean empty) {
                if (image != null && !empty){
                    final ImageView imageview = new ImageView();
                    imageview.setFitHeight(30);
                    imageview.setFitWidth(50);
                    imageview.setImage(image);
                    setGraphic(imageview);
                    setAlignment(Pos.CENTER);
                }else{
                    setGraphic(null);
                    setText(null);
                }
            };
        });

        Callback<TableColumn<Captcha,String>,TableCell<Captcha,String>> defaultTextFactory=TextFieldTableCell.forTableColumn();
        zutContent.setCellFactory(col -> {
            var cell=defaultTextFactory.call(col);

            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty()) {
                    cell.setEditable(true);
                }
            });
            return cell;
        });


        taula.setEditable(true);
        taula.setItems(emaitza);

    }

    @FXML
    void onClickGorde(ActionEvent event) {
        //TODO: value null bada ez gorde db-an value atributua...
        DBKudeatzaile.getInstantzia().execSQL("delete from captchas");

        emaitza.forEach(elem->{

            String query="insert into captchas (filename,value,date) values('"+
                    elem.getFilename()+"','"+
                    elem.getValue()+"','"+
                    elem.getDate()+"')";
            DBKudeatzaile.getInstantzia().execSQL(query);
        });
    }

    @FXML
    void onClickTxertatu(ActionEvent event) throws IOException {
        BufferedImage image;
        File file=File.createTempFile("captcha",".png", new File(Utils.lortuEzarpenak().getProperty("irudiak")));
        Captcha captcha=new Captcha();

        //System.getProperty("file.separator")+ "irudiak" +System.getProperty("file.separator")

        try{
            URL url=new URL("http://45.32.169.98/captcha.php");
            image= ImageIO.read(url);
            ImageIO.write(image,"png",file);

            String query="insert into captchas (filename,date) values('"+
                    captcha.getFilename()+"','"+
                    +captcha.getDate()+"')";
            DBKudeatzaile.getInstantzia().execSQL(query);

            ResultSet resultSet=DBKudeatzaile.getInstantzia().execSQL("SELECT * from captchas where filename='"+captcha.getFilename()+"'");

            resultSet.next();
            captcha.setId(resultSet.getInt("id"));
            captcha.setFilename(file.getName());
            captcha.setIrudia(new Image(file.toURI().toString()));
            captcha.setDate( System.currentTimeMillis());
            captcha.setData(new Date(captcha.getDate()).toString());

            emaitza.add(captcha);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    private void datuakSartu(ResultSet resultSet) throws SQLException {

        while (resultSet.next()){
            Captcha captcha=new Captcha();
            captcha.setId(resultSet.getInt("id"));
            captcha.setFilename(resultSet.getString("filename"));
            captcha.setValue(resultSet.getString("value"));
            captcha.setDate(resultSet.getInt("date"));
            captcha.setData(new Date(captcha.getDate()).toString());
            captcha.setIrudia(new Image(new File(Utils.lortuEzarpenak().getProperty("irudiak")+captcha.getFilename()).toURI().toString()));

            emaitza.add(captcha);
        }
    }
}
