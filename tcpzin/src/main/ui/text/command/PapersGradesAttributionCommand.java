package main.ui.text.command;

import java.util.List;
import java.util.Map;

import main.business.PapersManagementService;
import main.exceptions.BusinessDomainException;
import main.exceptions.BusinessServiceException;
import main.ui.text.UIUtils;

public class PapersGradesAttributionCommand implements ConferenceUICommand {

	private PapersManagementService papersManagementService;

	public PapersGradesAttributionCommand(
			PapersManagementService papersManagementService) {
		this.papersManagementService = papersManagementService;
	}

	/**
	 * requests a paper, a reviewer and a grade from the user. then it creates a
	 * review with those data.
	 * 
	 * @throws BusinessServiceException
	 * @throws BusinessDomainException
	 */
	public void execute() throws BusinessServiceException,
			BusinessDomainException {

		Integer paperId = readPaper();
		Integer reviewerId = readReviewer(paperId);
		double grade = readGrade();
		papersManagementService.setGradeToPaper(paperId, reviewerId, grade);
	}

	private Integer readPaper() throws BusinessServiceException {
		List<String> titles = papersManagementService.getPapersTitles();

		String chosen = UIUtils.chooseFromList(titles);
		Map<String, Integer> title2id = papersManagementService
				.getPapersTitlesAndIds();
		return title2id.get(chosen);
	}

	private Integer readReviewer(int paper) throws BusinessServiceException {
		List<String> possibleReviewers = papersManagementService
				.getReviewers(paper);
		String chosen = UIUtils.chooseFromList(possibleReviewers);
		Map<String, Integer> name2id = papersManagementService
				.getResearchersNamesAndIds();
		return name2id.get(chosen);
	}

	private double readGrade() {
		return UIUtils.readDouble("message.insertGrade");

	}
}
