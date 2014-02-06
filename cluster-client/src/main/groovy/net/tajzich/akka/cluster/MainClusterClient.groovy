package net.tajzich.akka.cluster

import akka.actor.ActorSystem
import akka.contrib.pattern.ClusterClient
import com.typesafe.config.ConfigFactory
import net.tajzich.akka.cluster.message.Calculate
import scala.concurrent.duration.Duration

import java.util.concurrent.TimeUnit

/**
 * Created by vtajzich on 05/02/14.
 */
class MainClusterClient {

    static void main(String[] args) {

        final ActorSystem system = ActorSystem.create("client1", ConfigFactory.load());

        Set initialContacts = [system.actorSelection("akka.tcp://ClusterSystem@127.0.0.1:2551/user/receptionist"),
                               system.actorSelection("akka.tcp://ClusterSystem@127.0.0.1:2552/user/receptionist")]

        def clusterClient = system.actorOf(ClusterClient.defaultProps(initialContacts), "os-client")

        system.scheduler().schedule(Duration.create(1, TimeUnit.SECONDS), Duration.create(1, TimeUnit.SECONDS), {
//            clusterClient.tell(new ClusterClient.SendToAll('/user/calculator', new Calculate(2, 3)), clusterClient)
            clusterClient.tell(new ClusterClient.Send('/user/calculator', new Calculate(2, 3), false), clusterClient)
        } as Runnable, system.dispatcher());
    }
}
