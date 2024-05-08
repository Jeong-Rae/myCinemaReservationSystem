package core.domain.movie;

public enum RatingType {
	G("전체관람가"),
	G12("12세이상관람가"),
	R15("15세이상관람가"),
	R18("청소년관람불가");

	private final String description;

	public String getDescription() {
		return this.description;
	}

	private RatingType(String description) {
		this.description = description;
	}
	
	public static RatingType descriptionToEnum(String description) {
		for (RatingType type : RatingType.values()) {
            if (type.getDescription().equals(description)) {
                return type;
            }
        }
		return null;
	}
}
