package main.business.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.exceptions.BusinessDomainException;

public class Paper implements Comparable<Paper> {
	private int id;
	private String title;
	private Researcher author;
	private Conference conference;
	private Topic researchTopic;
	private List<Review> reviews;

	/**
	 * STATICS
	 */

	private static int lastId = 0;

	public static Comparator<Paper> ascendingGradeComparator = new Comparator<Paper>() {
		public int compare(Paper p, Paper q) {
			Double diff = p.getAverageGrade() - q.getAverageGrade();
			if (diff > 0) {
				return 1;
			} else {
				return -1;
			}
		}
	};

	public static Comparator<Paper> descendingGradeComparator = new Comparator<Paper>() {
		public int compare(Paper p, Paper q) {
			Double diff = p.getAverageGrade() - q.getAverageGrade();
			if (diff < 0) {
				return 1;
			} else {
				return -1;
			}
		}
	};

	public static Comparator<Paper> ascendingIdComparator = new Comparator<Paper>() {
		public int compare(Paper p, Paper q) {
			return p.getId() - q.getId();
		}
	};

	public static Comparator<Paper> descendingIdComparator = new Comparator<Paper>() {
		public int compare(Paper p, Paper q) {
			return q.getId() - p.getId();
		}
	};

	public static List<Paper> sortPaper(List<Paper> papers,
			Comparator<Paper> comparator) {
		List<Paper> sorted = new ArrayList<Paper>(papers);
		Collections.sort(sorted, comparator);
		return sorted;
	}

	public static List<Paper> sortPaperByGrade(List<Paper> papers,
			boolean ascending) {
		if (ascending) {
			return sortPaper(papers, ascendingGradeComparator);
		} else {
			return sortPaper(papers, descendingGradeComparator);
		}
	}

	public static List<Paper> sortPaperById(List<Paper> papers,
			boolean ascending) {
		if (ascending) {
			return sortPaper(papers, ascendingIdComparator);
		} else {
			return sortPaper(papers, descendingIdComparator);
		}
	}

	/**
	 * NON-STATIC
	 */

	public Paper(int id, String title, Researcher author, Topic researchTopic,
			Conference conference, List<Review> reviews) {
		this.title = title;
		this.author = author;
		this.researchTopic = researchTopic;
		this.setConference(conference);
		this.reviews = reviews;
		this.id = id;

		/* last id must always be the largest id */
		lastId = (id > lastId) ? id : lastId;
	}

	public Paper(String title, Researcher author, Topic researchTopic) {
		this(lastId++, title, author, researchTopic, null,
				new ArrayList<Review>());
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Researcher getAuthor() {
		return author;
	}

	public void setConference(Conference conference) {
		if (conference != null) {
			conference.addPaper(this);
		}
		this.conference = conference;
	}

	public Conference getConference() {
		return conference;
	}

	public Topic getResearchTopic() {
		return researchTopic;
	}

	public void addReview(Review review) {
		if (!reviews.contains(review))
			reviews.add(review);
	}

	public void setGrade(Researcher reviewer, double grade)
			throws BusinessDomainException {
		if (!reviews.isEmpty()) {
			for (Review review : reviews) {
				if (review.getReviewer().equals(reviewer)) {
					review.setGrade(grade);
				}
			}
		} else {
			/* if no review is found with that reviewer */
			throw new BusinessDomainException(
					"exception.business.domain.noSuchReviewer");
		}
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public Double getAverageGrade() {
		if (!reviews.isEmpty()) {
			double total = 0.0;

			for (Review review : reviews) {
				total += review.getGrade();
			}
			return (total / reviews.size());
		} else {
			return null;
		}
	}

	public boolean hasPendingReviews() {
		if (reviews.isEmpty()) {
			return true;
		}

		for (Review review : reviews) {
			if (review.isPendingGrade())
				return true;
		}

		return false;
	}

	@Override
	public int compareTo(Paper other) {
		if (this.getId() > other.getId()) {
			return 1;
		} else if (this.getId() < other.getId()) {
			return -1;
		} else {
			return 0;
		}

	}

	@Override
	public String toString() {
		String output = "==> paper:\n";
		output += "id: " + id + "\n";
		output += "title: " + title + "\n";
		output += "author id: " + author.getId() + "\n";
		output += "conference: " + conference.getInitials() + "\n";
		output += "topic: " + researchTopic + "\n";
		output += "reviews:\n";

		for (Review rev : reviews) {
			int reviewer = rev.getReviewer().getId();
			String grade = rev.isPendingGrade() ? "pending" : String
					.valueOf(rev.getGrade());
			output += "- " + grade + " from " + reviewer + "\n";
		}

		return output;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Paper) {
			Paper other = (Paper) obj;
			return this.getId() == other.getId();
		} else {
			return false;
		}
	}

	public int hashCode() {
		return this.getId();
	}
}
