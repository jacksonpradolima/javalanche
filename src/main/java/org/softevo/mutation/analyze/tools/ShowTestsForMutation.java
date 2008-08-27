package org.softevo.mutation.analyze.tools;

import java.util.Set;


import org.softevo.mutation.results.Mutation;
import org.softevo.mutation.results.persistence.QueryManager;

public class ShowTestsForMutation {

	public static void main(String[] args) {
		if (args.length > 0) {
			Long l = Long.parseLong(args[0]);
			Mutation mutation = QueryManager.getMutationByID(l);
			Set<String> testsCollectedData = QueryManager
					.getTestsCollectedData(mutation);
//			System.out.println(" Tests for " + mutation);
			for (String string : testsCollectedData) {
				System.out.println(string);
			}
		}
	}

}