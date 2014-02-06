package net.tajzich.akka.cluster
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.cluster.Cluster
import akka.cluster.ClusterEvent
import akka.contrib.pattern.ClusterReceptionistExtension
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import net.tajzich.akka.cluster.actor.Calculator
import net.tajzich.akka.cluster.actor.Greeter
import net.tajzich.akka.cluster.actor.SimpleClusterListener
/**
 * Created by vtajzich on 05/02/14.
 */
class MainClusterMaster {

    static void main(String[] args) {

        String nodeType = args[1]

        Config conf = ConfigFactory.parseString("akka.remote.netty.tcp.port=${args[0]}").withFallback(ConfigFactory.load())

        final ActorSystem system = ActorSystem.create("ClusterSystem", conf)

        ActorRef clusterListener = system.actorOf(Props.create(SimpleClusterListener), "clusterListener")

        Cluster.get(system).subscribe(clusterListener as ActorRef, ClusterEvent.ClusterDomainEvent)

        ActorRef service = createService(system, nodeType)

        println "Registering service: $service"

        ClusterReceptionistExtension.get(system).registerService(service)
    }

    static ActorRef createService(ActorSystem system, String nodeType) {

        switch(nodeType) {

            case 'greeter':
                return system.actorOf(Props.create(Greeter), 'greeter')
            default:
                return system.actorOf(Props.create(Calculator), 'calculator')
        }
    }
}
