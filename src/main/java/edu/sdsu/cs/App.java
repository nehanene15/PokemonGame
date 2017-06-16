package edu.sdsu.cs;

import edu.sdsu.cs.datastructures.ArrayList;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;



/**
 * Neha Nene, cssc0669
 * Main Pokemon game driver. Takes in input file path and output file path to create.
 * Reads in creatures from input file and builds an array list of creatures.
 * The driver continues to simulate a total elimination war, randomly pairing creatures from the competitors.
 * The creatures fight another opponent per round, until one Pokemon is left as the survivor.
 *
 */
public class App {

    public static void main(String[] args) throws IOException {
        String infile = args[0];
        String outfile = args[1];

        BufferedReader br = null;
        PrintWriter writer = new PrintWriter(outfile, "UTF-8");
        String splitby = ",";
        ArrayList<Creature> creatures = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(infile));
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                Creature pokemon = new Creature();
                String[] readin = line.split(splitby);
                pokemon.setid(readin[0]);
                pokemon.setName(readin[1]);
                pokemon.setType1(readin[2]);
                pokemon.setType2(readin[3]);
                pokemon.setHp(readin[5]);

                pokemon.setAttack(readin[6]);
                pokemon.setDefense(readin[7]);
                pokemon.setSpAtk(readin[8]);
                pokemon.setSpDef(readin[9]);
                pokemon.setSpeed(readin[10]);
                pokemon.setGeneration(readin[11]);
                pokemon.setLegendary(Boolean.parseBoolean(readin[12]));

                creatures.add(pokemon);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        ArrayList <Creature> Vanquished = new ArrayList<Creature>();
        ArrayList<Creature> Survivors= Rounds(creatures, Vanquished, writer,1);
        writer.println("And the final survivor is: "+Survivors.get(0).toString());
        writer.close();
        System.out.println("Complete!");

    }

    private static ArrayList<Creature> GetSurvivors(ArrayList<Creature>competitors, ArrayList<Creature> losers, PrintWriter pw){ //returns winners of array passed in
        //Collections.shuffle(competitors); //shuffle
        ArrayList<Creature> NewSurvivors = new ArrayList<Creature>(); //initializing winners array
        if((competitors.size()% 2) !=0){ //sort based on combat score, then highest score adds on to NewSurvivors
            Collections.sort(competitors, new Comparator<Creature>() {
                public int compare(Creature o1, Creature o2) {
                    return o1.getCombatScore() > o2.getCombatScore() ? -1 : o1.getCombatScore() == o2.getCombatScore() ? 0 : 1;
                }
            });
            Creature top =competitors.get(0);
            competitors.remove(0);
            NewSurvivors.add(top);
            pw.println(top.getName()+ " automatically advances.");

        }
        Collections.shuffle(competitors); //shuffle

        for (int i = 0; i < competitors.size(); i += 2) {
            Creature pokemon1 = competitors.get(i); //get two random pokemon from creatures array list
            Creature pokemon2 = competitors.get(i + 1);
            boolean didwin = pokemon1.combat(pokemon2); //if wins, add pokemon to respective array lists
            if (didwin == true) { // if pokemon 1 wins
                if(pokemon2.isLegendary()==true && pokemon2.legendLost<3){ //if pokemon is legendary, it is not vanquished
                    NewSurvivors.add(pokemon2);
                    pokemon2.legendLost++;
                    pw.println(pokemon2.getName()+" is legendary and continues to next round. ");
                }
                else{
                    losers.add(pokemon2);
                    printStats(pokemon1,pokemon2,pw);
                }
                checkHeroic(pokemon1); //check if winner becomes heroic
                pokemon1.updateScore(pokemon2);
                NewSurvivors.add(pokemon1);
                pokemon1.winCount++;

            } else { // if pokemon 2 wins
                if(pokemon1.isLegendary()==true && pokemon1.legendLost<3){ //if pokemon is legendary, it is not vanquished
                    NewSurvivors.add(pokemon1);
                    pokemon1.legendLost++;
                    pw.println(pokemon1.getName()+" is legendary and continues to next round. ");
                }
                else{
                    losers.add(pokemon1);
                    printStats(pokemon2,pokemon1,pw);
                }
                checkHeroic(pokemon2);
                pokemon2.updateScore(pokemon1);
                NewSurvivors.add(pokemon2);
                pokemon2.winCount++;

            }
        }

        return NewSurvivors;
    }


    private static ArrayList<Creature> Rounds(ArrayList<Creature> competitors, ArrayList<Creature> losers, PrintWriter pw, Integer roundnum){ // simulates one elimination round
        if(roundnum != 1){
            pw.println("\n");
        }
        pw.println("Round " +roundnum+":"); //round header
        pw.println("Combatants: "+competitors.size);
        getTopFive(competitors,pw); //prints top five to leader board per round
        ArrayList<Creature> output= GetSurvivors(competitors,losers, pw);
        while(output.size()>1){
            roundnum++;
            return Rounds(output,losers,pw,roundnum); //recursively calls until Survivors is one creature

        }
        return output; //returns final survivor
    }

    private static void checkHeroic(Creature pokemon){ //check if creature has won three times to be promoted to heroic
        if(pokemon.isLegendary()==false && pokemon.winCount>=3){
            pokemon.setLegendary(true);
            pokemon.setHeroic(true);

        }

    }

    private static void printStats(Creature pokemon1, Creature pokemon2, PrintWriter pw){ //print line of combat outcome

            if (pokemon2.getHeroic()==true){
                pw.println(pokemon1.getName()+" finally vanquishes heroic "+ pokemon2.getName()+".");
            }
            else if (pokemon2.isLegendary()==true) {
                pw.println(pokemon1.getName() + " finally vanquishes legendary " + pokemon2.getName()+".");
            }


        else {
            pw.println(pokemon1.getName()+" defeats "+pokemon2.getName()+".");
        }
    }

    private static void getTopFive(ArrayList<Creature> competitors, PrintWriter pw){ //prints strongest creatures of each round
        Collections.sort(competitors, new Comparator<Creature>() { //sort based on top five combat scores
            public int compare(Creature o1, Creature o2) {
                return o1.getCombatScore() > o2.getCombatScore() ? -1 : o1.getCombatScore() == o2.getCombatScore() ? 0 : 1;
            }
        });
        pw.println("-----Leaders-----");

        if(competitors.size>5){
            pw.println(competitors.get(0).toString());
            pw.println(competitors.get(1).toString());
            pw.println(competitors.get(2).toString());
            pw.println(competitors.get(3).toString());
            pw.println(competitors.get(4).toString());
        }

        else if (competitors.size>=4){
            pw.println(competitors.get(0).toString());
            pw.println(competitors.get(1).toString());
            pw.println(competitors.get(2).toString());
            pw.println(competitors.get(3).toString());
        }

        else if (competitors.size>=3){
            pw.println(competitors.get(0).toString());
            pw.println(competitors.get(1).toString());
            pw.println(competitors.get(2).toString());
        }

        else if(competitors.size>=2){
            pw.println(competitors.get(0).toString());
            pw.println(competitors.get(1).toString());
            pw.println();
            return;
        }

        pw.println();
        pw.println("Begin!");
    }






}