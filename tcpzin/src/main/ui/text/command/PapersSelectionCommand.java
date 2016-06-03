package main.ui.text.command;

import main.business.PapersManagementService;
import main.business.domain.Conference;
import main.business.domain.Paper;
import main.ui.text.UIUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class PapersSelectionCommand implements ConferenceUICommand{

	private PapersManagementService papersManagementService;

	public void PapersSelectionCommand(PapersManagementService papersManagementService) {
		this.papersManagementService = papersManagementService;
	}

	public void execute(){

	}

	private Conference readConference() {
		return null;
	}

	private void showGradeMissingAlert() {

	}


	private void showAccRejLists(Map<Paper,Boolean> listsMap) {
		List<Paper> allPapers = papersManagementService.GetAllPapers();
		List<Paper> rejectedList = null;
		List<Paper> acceptedList = null;

		for (Paper paper : allPapers){
			boolean isRejected = (listsMap.get(paper) == false);
			if(isRejected){
				rejectedList.add(paper);
				//TODO SORT OF LIST	
			}else{
				acceptedList.add(paper);
				//TODO SORT OF LIST
			}
		}
		System.out.println(UIUtils.getText("message.artigosRejeitados"));
		for (Paper rejPaper : rejectedList){
			System.out.println(UIUtils.getText("message.message.paperId") + ": " + rejPaper.getId() + UIUtils.getText("message.message.paperTitle") + ": " + rejPaper.getTitle());

		}
		System.out.println(UIUtils.getText("message.artigosAceitos"));
		for (Paper accPaper : acceptedList){
			System.out.println(UIUtils.getText("message.message.paperId") + ": " + accPaper.getId() + UIUtils.getText("message.message.paperTitle") + ": " + accPaper.getTitle());

		}



	}
}
