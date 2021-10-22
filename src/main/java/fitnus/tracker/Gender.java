package fitnus.tracker;


public enum Gender {
    MALE {
        @Override
        public String toString() {
            return "Male";
        }
    },
    FEMALE {
        @Override
        public String toString() {
            return "Female";
        }
    };

    public static Gender findGender(String gender) {
        if (gender.toLowerCase().equals("m")) {
            return Gender.MALE;
        } else if (gender.toLowerCase().equals("f")) {
            return Gender.FEMALE;
        }
        return null;
    }
}


