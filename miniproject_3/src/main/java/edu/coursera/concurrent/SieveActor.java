package edu.coursera.concurrent;

import static edu.rice.pcdp.PCDP.finish;

import java.util.ArrayList;
import java.util.List;

import edu.rice.pcdp.Actor;

/**
 * An actor-based implementation of the Sieve of Eratosthenes.
 *
 * TODO Fill in the empty SieveActorActor actor class below and use it from
 * countPrimes to determin the number of primes <= limit.
 */
public final class SieveActor extends Sieve {

    //private boolean[] localPrimes;
    //private int limit = -1;


    /**
     * {@inheritDoc}
     *
     * TODO Use the SieveActorActor class to calculate the number of primes <=
     * limit in parallel. You might consider how you can model the Sieve of
     * Eratosthenes as a pipeline of actors, each corresponding to a single
     * prime number.
     */
    @Override
    public int countPrimes(final int limit) {

        SieveActorActor actor = new SieveActorActor();

        actor.primes = new ArrayList<Integer>();

        finish(() -> {

            actor.send(2);


            for(int i=3; i<=limit; i+= 2 ){
                actor.send(i);
            }
        });

        //actor.send(0);


        int count = 0;


        //while(count < 100){
        //    System.out.println(actor.primes.get(count));

//            count++;
  //      }

        return actor.primes.size();



    }

    /**
     * An actor class that helps implement the Sieve of Eratosthenes in
     * parallel.
     */
    public static final class SieveActorActor extends Actor {

        public static List<Integer> primes = new ArrayList<Integer>();

        private int divisor = -1;

        SieveActorActor nextActor = null;// = new SieveActorActor();

        /**
         * Process a single message sent to this actor.
         *
         * TODO complete this method.
         *
         * @param msg Received message
         */
        @Override
        public void process(final Object msg) {

            int candidate = (Integer) msg;

            if(divisor == -1){

                divisor = candidate;

                primes.add(candidate);
                // add to primes

            }else{


                if(candidate % divisor != 0) {

                    if(nextActor == null){
                        nextActor = new SieveActorActor();
                    }

                    nextActor.send(candidate);
                }

            }





        }






    }
}
