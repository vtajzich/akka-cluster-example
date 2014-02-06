package net.tajzich.akka.cluster.actor

import akka.actor.UntypedActor
import net.tajzich.akka.cluster.message.Greeting

/**
 * Created by vtajzich on 06/02/14.
 */
class Greeter extends UntypedActor {

    @Override
    void onReceive(Object message) throws Exception {

        if(message instanceof Greeting) {

            Greeting greeting = message as Greeting

            String response = "$greeting my lord!"

            println response

            sender().tell(response, getSelf())

        } else {
            unhandled(message)
        }
    }
}
