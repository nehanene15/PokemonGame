package edu.sdsu.cs;

/**
 * Created by Neha Nene, cssc0669.
 * Creature class for unique instances of pokemon.
 * Includes methods for combat between two Creatures.
 */

public class Creature implements Comparable<Creature> {
    String name=null;
    String type1=null;
    String type2=null;
    int id=0;
    int hp = 0;
    int attack = 0;
    int defense=0;
    int spatk=0;
    int spdef=0;
    int speed=0 ;
    int total=0;
    int gen =0;
    boolean legendary;
    int index=0;
    int legendLost=0;
    int winCount=0;
    boolean heroic;

    // member variables

    public void setid(String id){
        this.id=Integer.parseInt(id);
    }
    public int getid(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        if(type2.isEmpty()){
             return "";
        }
        else {
            return  "/" + type2;
        }
    }


    public void setType2(String type2) {
        this.type2 = type2;
    }

    public int getTotal() {
        return hp+attack+defense+spatk+spdef+speed;

    }


    public int getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = Integer.parseInt(hp);
    }


    public void setAttack(String attack) {
        this.attack = Integer.parseInt(attack);
    }

    public void setDefense(String defense) {
        this.defense = Integer.parseInt(defense);
    }


    public void setSpAtk(String spAtk) {
        this.spatk = Integer.parseInt(spAtk);
    }


    public void setSpDef(String spDef) {
        this.spdef = Integer.parseInt(spDef);}

    public void setSpeed(String speed) {
        this.speed = Integer.parseInt(speed);
    }

    public void setGeneration(String generation) {
        this.gen = Integer.parseInt(generation);
    }

    public boolean isLegendary() {
        return legendary;
    }

    public void setHeroic(boolean heroic){
        this.heroic = heroic;
    }
    public boolean getHeroic(){
        return heroic;
    }

    public void setLegendary(boolean legendary) {
        this.legendary = legendary;
    }

    public int getCombatScore() { //returns calculated combat score
        return (this.speed/2) * (this.attack + (this.spatk/2)) + (this.defense + (this.spdef/2));
    }


    public void updateScore(Creature loser){ //if Creature wins battle, updates scores
        double increase = loser.getTotal()/2.0;
        this.attack +=increase;
        this.defense+=increase;
    }


    public int compareTo(Creature other) { //use for natural order if same combat score
        if( this.getTotal()== other.getTotal()){
            return 0;
        }
        if(this.getTotal()>other.getTotal()){
            return 1;
        }
        return -1;
    }




    @Override
    public String toString() { //returns statistics of creature
        return this.getName()+"    "+ " [Type: " + this.getType1() + this.getType2()+"][ HP: " + this.getHp()+"]";

    }

    public boolean combat(Creature pokemon2){ //returns winner of combat between two creatures
        if(this.getCombatScore()>pokemon2.getCombatScore()){
            return true;
        }else if(this.getCombatScore()==pokemon2.getCombatScore()){ //if tied, compares natural order
            if(this.compareTo(pokemon2)>0){
                return true;
            } else if(this.getTotal()==pokemon2.getTotal()){
                if(this.getName().compareTo(pokemon2.getName()) >0 ){ //if still tied, compares names alphabetically
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }

    }



}
