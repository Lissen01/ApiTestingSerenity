import facts.NexflixPlans;
import models.users.Datum;
import models.users.RegisterUserInfo;
import models.users.UpdateUserInfo;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import questions.GetUsersQuestion;
import questions.ResponseCode;
import tasks.GetUsers;
import tasks.PostRegisterUser;
import tasks.PutUpdateUser;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(SerenityRunner.class)
public class SerenityInitialTest {

    private static final String restApiUrl = "http://localhost:5000/api/"; //BaseUrl

    //Get
    @Test
    public void getUsers() { //Creo un actor mediante named para hacer un llamado a la api mediante la baseUrl
        Actor uriel = Actor.named("The tester")
                .whoCan(CallAnApi.at(restApiUrl));
        uriel.attemptsTo(
                GetUsers.fromPage(1)
        );
        //assertThat(SerenityRest.lastResponse().statusCode()).isEqualTo(200);
        uriel.should(
                seeThat("el codigo de respuesta:", ResponseCode.was(), equalTo(200))
        );
        Datum user = new GetUsersQuestion().answeredBy(uriel)
                .getData().stream().filter(x -> x.getId() == 1).findFirst().orElse(null);
        //Asercion para verificar que el usuario no es nulo
        uriel.should(
                seeThat("usuario no es nulo", act -> user, notNullValue())
        );
        //Asercion para verificar el email obtenido con respecto al body obtenido
        uriel.should(
                seeThat("el email del usuario:", act -> user.getEmail(), equalTo("george.bluth@reqres.in")),
                seeThat("el avatar del usuario:", act -> user.getAvatar(), equalTo("https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg"))
        );
    }

    //POST
    //Sin modelado haciendo un task que haga una verificacion mediante la clase userInfo
    @Test
    public void postRegisterUserTest() { //Creo un actor mediante named para hacer un llamado a la api mediante la baseUrl
        Actor uriel = Actor.named("The tester")
                .whoCan(CallAnApi.at(restApiUrl));
        String registerUserInfo = "{\n" +
                "\t\"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\",\n" +
                "    \"email\": \"tobias.funke@reqres.in\",\n" +
                "    \"password\": \"serenity\"\n" +
                "}";

        uriel.attemptsTo(
                PostRegisterUser.withInfo(registerUserInfo)
        );

        uriel.should(
                seeThat("el codigo de respuesta es:", ResponseCode.was(), equalTo(200))
        );
    }

    //Con modelado es lo ideal, hacer un objeto tipo object para traer dar los datos mediante setters en la clase modelo
    @Test
    public void postRegisterUserTest2() { //Creo un actor mediante named para hacer un llamado a la api mediante la baseUrl
        Actor uriel = Actor.named("The tester")
                .whoCan(CallAnApi.at(restApiUrl));
        RegisterUserInfo registerUserInfo = new RegisterUserInfo();
        registerUserInfo.setName("morpheus");
        registerUserInfo.setJob("leader");
        registerUserInfo.setEmail("tobias.funke@reqres.in");
        registerUserInfo.setPassword("serenity");

        uriel.attemptsTo(
                PostRegisterUser.withInfo(registerUserInfo)
        );

        uriel.should(
                seeThat("el codigo de respuesta es:", ResponseCode.was(), equalTo(200))
        );
    }

    //PUT
    @Test
    public void putUpdateUserTest() { //Creo un actor mediante named para hacer un llamado a la api mediante la baseUrl
        Actor uriel = Actor.named("The tester")
                .whoCan(CallAnApi.at(restApiUrl));
        UpdateUserInfo updateUserInfo = new UpdateUserInfo();
        updateUserInfo.setName("morpheus");
        updateUserInfo.setJob("zion resident");
        updateUserInfo.setEmail("tobias.funke@reqres.in");
        updateUserInfo.setPassword("serenity");

        uriel.attemptsTo(
                PutUpdateUser.withInfo(updateUserInfo)
        );

        uriel.should(
                seeThat("el codigo de respuesta es:", ResponseCode.was(), equalTo(200))
        );
    }

    @Test
    public void factTest() { //Creo un actor mediante named para hacer un llamado a la api mediante la baseUrl
        Actor uriel = Actor.named("The tester")
                .whoCan(CallAnApi.at(restApiUrl));
        uriel.has(NexflixPlans.toViewSeries());

        uriel.should(
                seeThat("status code: ", ResponseCode.was(), equalTo(200))
        );
    }
}
