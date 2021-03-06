package main.ui.text.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.business.PapersManagementService;
import main.exceptions.BusinessDomainException;
import main.exceptions.BusinessServiceException;
import main.ui.text.UIUtils;

public class PapersAllocationCommand implements ConferenceUICommand {

	private PapersManagementService papersManagementService;

	public PapersAllocationCommand(
			PapersManagementService papersManagementService) {
		this.papersManagementService = papersManagementService;
	}

	public void execute() throws BusinessServiceException,
			BusinessDomainException {

		String conference = readConference();
		Integer numReviewers = UIUtils
				.readInteger("message.insertNumReviewers");
		allocate(conference, numReviewers);

	}

	private void allocate(String conference, int numReviewers)
			throws BusinessServiceException, BusinessDomainException {
		Map<Integer, List<Integer>> reviewer2papers = new HashMap<Integer, List<Integer>>();

		System.out.println(UIUtils.getText("message.startingAllocation"));
		reviewer2papers = papersManagementService.allocPapersToReviewers(
				conference, numReviewers);
		showAllocationLog(reviewer2papers);
		System.out.println(UIUtils.getText("message.endOfAllocation"));
	}

	private String readConference() throws BusinessServiceException {
		List<String> allConferences = papersManagementService
				.getConferencesInitials();
		String chosen = UIUtils.chooseFromList(allConferences);
		return chosen;

	}

	private void showAllocationLog(Map<Integer, List<Integer>> reviewer2papers) {
		for (Map.Entry<Integer, List<Integer>> entry : reviewer2papers
				.entrySet()) {
			for (Integer paper : entry.getValue()) {
				System.out.print(UIUtils.getText("message.thisPaper") + " "
						+ paper + " ");
				System.out.println(UIUtils.getText("message.wasAllocedTo")
						+ " " + entry.getKey());
			}
		}
	}

}
