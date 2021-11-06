//@@author: siyuancheng178

package fitnus.tracker;


public enum Gender {
    MALE {
        @Override
        public String toString() {
            return "m";
        }
    },
    FEMALE {
        @Override
        public String toString() {
            return "f";
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


