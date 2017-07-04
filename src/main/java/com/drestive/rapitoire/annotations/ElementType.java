package com.drestive.rapitoire.annotations;

/**
 * Created by mustafa on 24/10/2016.
 */
public enum ElementType {
    STRING {
        @Override
        public String getName() {
            return "string";
        }
    },
    SELECT {
        @Override
        public String getName() {
            return "select";
        }
    },
    DATE {
        @Override
        public String getName() {
            return "date";
        }
    },
    TEXT {
        @Override
        public String getName() {
            return "text";
        }
    },
    URL {
        @Override
        public String getName() {
            return "url";
        }
    },
    BOOLEAN {
        @Override
        public String getName() {
            return "boolean";
        }
    },
    DECIMAL {
        @Override
        public String getName() {
            return "decimal";
        }
    },
    INTEGER {
        @Override
        public String getName() {
            return "integer";
        }
    };


    public String getName() {
        return "";
    }
}
