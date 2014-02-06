package net.tajzich.akka.cluster

import akka.actor.ActorSystem
import akka.contrib.pattern.ClusterClient
import com.typesafe.config.ConfigFactory
import net.tajzich.akka.cluster.message.Calculate
import net.tajzich.akka.cluster.message.Greeting
import scala.concurrent.duration.Duration

import java.util.concurrent.TimeUnit

/**
 * Created by vtajzich on 05/02/14.
 */
class MainClusterClient {

    static void main(String[] args) {

        String type = args[0]

        final ActorSystem system = ActorSystem.create("client1", ConfigFactory.load());

        Set initialContacts = [system.actorSelection("akka.tcp://ClusterSystem@127.0.0.1:2551/user/receptionist"),
                               system.actorSelection("akka.tcp://ClusterSystem@127.0.0.1:2552/user/receptionist")]

        def clusterClient = system.actorOf(ClusterClient.defaultProps(initialContacts), "os-client")

        def message = createMessage(type)

        system.scheduler().schedule(Duration.create(1, TimeUnit.SECONDS), Duration.create(1, TimeUnit.SECONDS), {

            String path = "/user/$type"

            println "Sending message: $message to $path"

            clusterClient.tell(new ClusterClient.Send(path, message, false), clusterClient)

        } as Runnable, system.dispatcher());
    }

    static def createMessage(String type) {

        switch(type) {
            case 'greeter':
                return new Greeting(generateGreeting())
            default:
                return new Calculate(generateNumber(), generateNumber())
        }
    }

    static int generateNumber() {
        return new Random().nextInt(11)
    }

    static String generateGreeting() {
        switch(generateNumber()) {

            case 1:
                return 'Hello'
            case 2:
                return 'Hi'
            case 3:
                return 'Morning'
            case 4:
                return 'Wat?!'
            case 5:
                return 'Hmm'
            default:
                return 'Good morning my lord'
        }
    }
}
