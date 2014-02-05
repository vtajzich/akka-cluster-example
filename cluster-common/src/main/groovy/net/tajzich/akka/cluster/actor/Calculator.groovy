package net.tajzich.akka.cluster.actor

import akka.actor.UntypedActor
import net.tajzich.akka.cluster.message.Calculate

/**
 * Created by vtajzich on 29/01/14.
 */
class Calculator extends UntypedActor {

    @Override
    void onReceive(Object message) throws Exception {

        println "Calculator received some message!"

        if (message instanceof Calculate) {

            Calculate calculate = message as Calculate

            def result = calculate.left + calculate.right

            println "Caculated results: $result from: ${sender}"

//            sender.tell(result, self)

        } else {
            unhandled(message)
        }

    }
}
