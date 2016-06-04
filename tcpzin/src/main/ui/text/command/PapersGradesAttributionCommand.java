package main.ui.text.command;

import java.util.List;
import java.util.Map;

import main.business.PapersManagementService;
import main.business.domain.Paper;
import main.business.domain.Researcher;
import main.business.domain.Review;
import main.business.impl.PapersManagementServiceImpl;
import main.exceptions.BusinessDomainException;
import main.ui.text.UIUtils;

public class PapersGradesAttributionCommand implements ConferenceUICommand {

	private PapersManagementService papersManagementService;

	public PapersGradesAttributionCommand(
			PapersManagementService papersManagementService) {
		this.papersManagementService = papersManagementService;
	}

	public void execute() {

		try {
			Paper paper = readPaper();
			Researcher reviewer = readReviewer(paper);
			double grade = readGrade();
			papersManagementService.setGradeToPaper(paper, reviewer, grade);
		} catch (BusinessDomainException e) {
			System.out.println(e.getMessage());
		}

	}

	private Paper readPaper() throws BusinessDomainException{
		Paper chosenPaper = null;
		List<Paper> allPapers = papersManagementService.getAllPapers();
		if (allPapers != null) {
			System.out.println(UIUtils.getText("message.todosPapers") + " ");
			for (Paper paper : allPapers) {
				System.out.println(UIUtils.getText("message.paperId") + ": "
						+ paper.getId() + UIUtils.getText("message.paperTitle")
						+ ": " + paper.getTitle());
			}
			int idPaper = UIUtils.readInteger("message.insiraIdPaper");
			for (Paper paper : allPapers) {
				boolean isChosenPaper = paper.getId() == idPaper;
				if (isChosenPaper) {
					chosenPaper = paper;
					break;
				}
			}
		} else {
			throw new BusinessDomainException(UIUtils.getText("exception.naoTemPapers"));
		}

		return chosenPaper;

	}

	private Researcher readReviewer(Paper paper) throws BusinessDomainException {
		List<Researcher> allReviewers = null;
		Researcher chosenReviewer = null;
		List<Review> allPapersReviews = paper.getReviews();
		if (allPapersReviews != null) {
			for (Review review : allPapersReviews) {
				allReviewers.add(review.getReviewer());
			}
			System.out.println(UIUtils.getText("message.todosReviewers"));

			for (Researcher reviewer : allReviewers) {
				System.out.println(UIUtils.getText("message.reviewerId") + ": "
						+ reviewer.getId()
						+ UIUtils.getText("message.reviewerName") + ": "
						+ reviewer.getName());
			}
			int idReviewer = UIUtils.readInteger("message.insiraIdRevisor");
			for (Researcher reviewer : allReviewers) {
				boolean isChosenReviewer = reviewer.getId() == idReviewer;
				if (isChosenReviewer) {
					chosenReviewer = reviewer;
					break;
				}
			}

		} else {
			throw new BusinessDomainException(UIUtils.getText("exception.naoTemReviewers"));
		}

		return chosenReviewer;
	}

	private double readGrade() {
		return UIUtils.readDouble("message.insiraNota");
	}

}
