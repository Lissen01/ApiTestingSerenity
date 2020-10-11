package facts;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.facts.Fact;
import net.serenitybdd.screenplay.rest.interactions.Get;

import java.util.HashMap;
import java.util.List;

public class NexflixPlans implements Fact {
    private String plansInfo;
    public static NexflixPlans toViewSeries(){
        return new NexflixPlans();
    }
    @Override
    public void setup(Actor actor) {
        actor.attemptsTo(
                Get.resource("plans").with(
                        requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                )
        );
        //Se obtiene a traves del hashmap los datos a traves de serenity rest, mediante la ultima respuesta dada por el path, en este caso el array DATA de tamaño 3
        List<HashMap<String, String>> plans = SerenityRest.lastResponse().path("data");

        actor.remember("plans", plans);

        plansInfo = plans.toString();
    }
    public String toString(){
        return "Los planes son: " +plansInfo;
    }
}
