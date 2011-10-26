package put.skio.ttt;
import ec.EvolutionState;
import ec.Individual;
import ec.Problem;
import ec.simple.SimpleFitness;
import ec.simple.SimpleProblemForm;
import ec.vector.DoubleVectorIndividual;
import games.player.Player;
import games.player.WPCPlayer;
import games.scenario.GameScenario;


public class TTTProblem extends Problem implements SimpleProblemForm {

	private static final int NUM_ROUNDS = 30;

	public void evaluate(EvolutionState state, Individual ind, int subpopulation, int threadnum) {
		if (!(ind instanceof DoubleVectorIndividual)) {
			state.output.fatal("Individual is not DoubleVectorIndividual");
		} 
		
		DoubleVectorIndividual wpcIndividual = (DoubleVectorIndividual) ind;
		double[] wpc = wpcIndividual.genome;
		Player myPlayer = new WPCPlayer(wpc);
		Player randomOpponent = new WPCPlayer();
		Player[] players = new Player[] {myPlayer, randomOpponent};
				
		int sumResult = 0;
		for (int i = 0; i < NUM_ROUNDS; i++) {			
			GameScenario gs = new GameScenario(players, new double[] {0.0, 1.0});
			sumResult += gs.play(new TTTGame());
		}
		
		((SimpleFitness)ind.fitness).setFitness(state, sumResult, false);
	}

}
