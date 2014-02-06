package net.tajzich.akka.cluster
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.cluster.Cluster
import akka.cluster.ClusterEvent
import akka.contrib.pattern.ClusterReceptionistExtension
import com.typesafe.config.ConfigFactory
import net.tajzich.akka.cluster.actor.Calculator
import net.tajzich.akka.cluster.actor.SimpleClusterListener
/**
 * Created by vtajzich on 05/02/14.
 */
class MainClusterMaster {

    static void main(String[] args) {

        /*if (args.length > 0) {
            System.setProperty("akka.remote.netty.tcp.port", args[0])
        }*/

        final ActorSystem system = ActorSystem.create("ClusterSystem", ConfigFactory.load())

        // Create an actor that handles cluster domain events
        ActorRef clusterListener = system.actorOf(Props.create(SimpleClusterListener), "clusterListener")

        // Add subscription of cluster events
        Cluster.get(system).subscribe(clusterListener as ActorRef, ClusterEvent.ClusterDomainEvent)

        ActorRef calculator = system.actorOf(Props.create(Calculator), "calculator")

        ClusterReceptionistExtension.get(system).registerService(calculator)
    }
}
