package com.capstone.datamate.Entity;

import java.util.List;

public class RequestEntities {

    public static class UpdateValuesRequest {
        private String tableName;
        private List<String> headers;
        private List<String> values;
        private String conditions; // For specifying conditions like "id = 1"

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public List<String> getHeaders() {
            return headers;
        }

        public void setHeaders(List<String> headers) {
            this.headers = headers;
        }

        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }

        public String getConditions() {
            return conditions;
        }

        public void setConditions(String conditions) {
            this.conditions = conditions;
        }
    }

    public static class InsertValuesRequest {
        private String tableName;
        private List<String> headers;
        private List<String> values;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public List<String> getHeaders() {
            return headers;
        }

        public void setHeaders(List<String> headers) {
            this.headers = headers;
        }

        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }
    }

    public interface TableRequest {
        String getTableName();
        void setTableName(String tableName);
        String getIdColumn();
        void setIdColumn(String idColumn);
        String getIdValue();
        void setIdValue(String idValue);
    }

    public static abstract class BaseTableRequest implements TableRequest {
        protected String tableName;
        protected String idColumn;
        protected String idValue;

        @Override
        public String getTableName() { return tableName; }
        @Override
        public void setTableName(String tableName) { this.tableName = tableName; }
        @Override
        public String getIdColumn() { return idColumn; }
        @Override
        public void setIdColumn(String idColumn) { this.idColumn = idColumn; }
        @Override
        public String getIdValue() { return idValue; }
        @Override
        public void setIdValue(String idValue) { this.idValue = idValue; }
    }

    public static class IdCheckRequest extends BaseTableRequest {}

    public static class RowDataRequest extends BaseTableRequest {}

    public static class DeleteRowRequest extends BaseTableRequest {}
}
