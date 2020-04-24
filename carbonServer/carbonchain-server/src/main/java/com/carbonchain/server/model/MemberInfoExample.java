package com.carbonchain.server.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public MemberInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andMemberIdIsNull() {
            addCriterion("member_id is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNotNull() {
            addCriterion("member_id is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdEqualTo(String value) {
            addCriterion("member_id =", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotEqualTo(String value) {
            addCriterion("member_id <>", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThan(String value) {
            addCriterion("member_id >", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThanOrEqualTo(String value) {
            addCriterion("member_id >=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThan(String value) {
            addCriterion("member_id <", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThanOrEqualTo(String value) {
            addCriterion("member_id <=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLike(String value) {
            addCriterion("member_id like", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotLike(String value) {
            addCriterion("member_id not like", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdIn(List<String> values) {
            addCriterion("member_id in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotIn(List<String> values) {
            addCriterion("member_id not in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdBetween(String value1, String value2) {
            addCriterion("member_id between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotBetween(String value1, String value2) {
            addCriterion("member_id not between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andPublicKeyIsNull() {
            addCriterion("public_key is null");
            return (Criteria) this;
        }

        public Criteria andPublicKeyIsNotNull() {
            addCriterion("public_key is not null");
            return (Criteria) this;
        }

        public Criteria andPublicKeyEqualTo(String value) {
            addCriterion("public_key =", value, "publicKey");
            return (Criteria) this;
        }

        public Criteria andPublicKeyNotEqualTo(String value) {
            addCriterion("public_key <>", value, "publicKey");
            return (Criteria) this;
        }

        public Criteria andPublicKeyGreaterThan(String value) {
            addCriterion("public_key >", value, "publicKey");
            return (Criteria) this;
        }

        public Criteria andPublicKeyGreaterThanOrEqualTo(String value) {
            addCriterion("public_key >=", value, "publicKey");
            return (Criteria) this;
        }

        public Criteria andPublicKeyLessThan(String value) {
            addCriterion("public_key <", value, "publicKey");
            return (Criteria) this;
        }

        public Criteria andPublicKeyLessThanOrEqualTo(String value) {
            addCriterion("public_key <=", value, "publicKey");
            return (Criteria) this;
        }

        public Criteria andPublicKeyLike(String value) {
            addCriterion("public_key like", value, "publicKey");
            return (Criteria) this;
        }

        public Criteria andPublicKeyNotLike(String value) {
            addCriterion("public_key not like", value, "publicKey");
            return (Criteria) this;
        }

        public Criteria andPublicKeyIn(List<String> values) {
            addCriterion("public_key in", values, "publicKey");
            return (Criteria) this;
        }

        public Criteria andPublicKeyNotIn(List<String> values) {
            addCriterion("public_key not in", values, "publicKey");
            return (Criteria) this;
        }

        public Criteria andPublicKeyBetween(String value1, String value2) {
            addCriterion("public_key between", value1, value2, "publicKey");
            return (Criteria) this;
        }

        public Criteria andPublicKeyNotBetween(String value1, String value2) {
            addCriterion("public_key not between", value1, value2, "publicKey");
            return (Criteria) this;
        }

        public Criteria andNickNameIsNull() {
            addCriterion("nick_name is null");
            return (Criteria) this;
        }

        public Criteria andNickNameIsNotNull() {
            addCriterion("nick_name is not null");
            return (Criteria) this;
        }

        public Criteria andNickNameEqualTo(String value) {
            addCriterion("nick_name =", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotEqualTo(String value) {
            addCriterion("nick_name <>", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameGreaterThan(String value) {
            addCriterion("nick_name >", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameGreaterThanOrEqualTo(String value) {
            addCriterion("nick_name >=", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameLessThan(String value) {
            addCriterion("nick_name <", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameLessThanOrEqualTo(String value) {
            addCriterion("nick_name <=", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameLike(String value) {
            addCriterion("nick_name like", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotLike(String value) {
            addCriterion("nick_name not like", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameIn(List<String> values) {
            addCriterion("nick_name in", values, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotIn(List<String> values) {
            addCriterion("nick_name not in", values, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameBetween(String value1, String value2) {
            addCriterion("nick_name between", value1, value2, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotBetween(String value1, String value2) {
            addCriterion("nick_name not between", value1, value2, "nickName");
            return (Criteria) this;
        }

        public Criteria andRoleIsNull() {
            addCriterion("role is null");
            return (Criteria) this;
        }

        public Criteria andRoleIsNotNull() {
            addCriterion("role is not null");
            return (Criteria) this;
        }

        public Criteria andRoleEqualTo(String value) {
            addCriterion("role =", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotEqualTo(String value) {
            addCriterion("role <>", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleGreaterThan(String value) {
            addCriterion("role >", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleGreaterThanOrEqualTo(String value) {
            addCriterion("role >=", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleLessThan(String value) {
            addCriterion("role <", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleLessThanOrEqualTo(String value) {
            addCriterion("role <=", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleLike(String value) {
            addCriterion("role like", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotLike(String value) {
            addCriterion("role not like", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleIn(List<String> values) {
            addCriterion("role in", values, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotIn(List<String> values) {
            addCriterion("role not in", values, "role");
            return (Criteria) this;
        }

        public Criteria andRoleBetween(String value1, String value2) {
            addCriterion("role between", value1, value2, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotBetween(String value1, String value2) {
            addCriterion("role not between", value1, value2, "role");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andBindedDeviceIsNull() {
            addCriterion("binded_device is null");
            return (Criteria) this;
        }

        public Criteria andBindedDeviceIsNotNull() {
            addCriterion("binded_device is not null");
            return (Criteria) this;
        }

        public Criteria andBindedDeviceEqualTo(String value) {
            addCriterion("binded_device =", value, "bindedDevice");
            return (Criteria) this;
        }

        public Criteria andBindedDeviceNotEqualTo(String value) {
            addCriterion("binded_device <>", value, "bindedDevice");
            return (Criteria) this;
        }

        public Criteria andBindedDeviceGreaterThan(String value) {
            addCriterion("binded_device >", value, "bindedDevice");
            return (Criteria) this;
        }

        public Criteria andBindedDeviceGreaterThanOrEqualTo(String value) {
            addCriterion("binded_device >=", value, "bindedDevice");
            return (Criteria) this;
        }

        public Criteria andBindedDeviceLessThan(String value) {
            addCriterion("binded_device <", value, "bindedDevice");
            return (Criteria) this;
        }

        public Criteria andBindedDeviceLessThanOrEqualTo(String value) {
            addCriterion("binded_device <=", value, "bindedDevice");
            return (Criteria) this;
        }

        public Criteria andBindedDeviceLike(String value) {
            addCriterion("binded_device like", value, "bindedDevice");
            return (Criteria) this;
        }

        public Criteria andBindedDeviceNotLike(String value) {
            addCriterion("binded_device not like", value, "bindedDevice");
            return (Criteria) this;
        }

        public Criteria andBindedDeviceIn(List<String> values) {
            addCriterion("binded_device in", values, "bindedDevice");
            return (Criteria) this;
        }

        public Criteria andBindedDeviceNotIn(List<String> values) {
            addCriterion("binded_device not in", values, "bindedDevice");
            return (Criteria) this;
        }

        public Criteria andBindedDeviceBetween(String value1, String value2) {
            addCriterion("binded_device between", value1, value2, "bindedDevice");
            return (Criteria) this;
        }

        public Criteria andBindedDeviceNotBetween(String value1, String value2) {
            addCriterion("binded_device not between", value1, value2, "bindedDevice");
            return (Criteria) this;
        }

        public Criteria andAuthSignPukIsNull() {
            addCriterion("auth_sign_puk is null");
            return (Criteria) this;
        }

        public Criteria andAuthSignPukIsNotNull() {
            addCriterion("auth_sign_puk is not null");
            return (Criteria) this;
        }

        public Criteria andAuthSignPukEqualTo(String value) {
            addCriterion("auth_sign_puk =", value, "authSignPuk");
            return (Criteria) this;
        }

        public Criteria andAuthSignPukNotEqualTo(String value) {
            addCriterion("auth_sign_puk <>", value, "authSignPuk");
            return (Criteria) this;
        }

        public Criteria andAuthSignPukGreaterThan(String value) {
            addCriterion("auth_sign_puk >", value, "authSignPuk");
            return (Criteria) this;
        }

        public Criteria andAuthSignPukGreaterThanOrEqualTo(String value) {
            addCriterion("auth_sign_puk >=", value, "authSignPuk");
            return (Criteria) this;
        }

        public Criteria andAuthSignPukLessThan(String value) {
            addCriterion("auth_sign_puk <", value, "authSignPuk");
            return (Criteria) this;
        }

        public Criteria andAuthSignPukLessThanOrEqualTo(String value) {
            addCriterion("auth_sign_puk <=", value, "authSignPuk");
            return (Criteria) this;
        }

        public Criteria andAuthSignPukLike(String value) {
            addCriterion("auth_sign_puk like", value, "authSignPuk");
            return (Criteria) this;
        }

        public Criteria andAuthSignPukNotLike(String value) {
            addCriterion("auth_sign_puk not like", value, "authSignPuk");
            return (Criteria) this;
        }

        public Criteria andAuthSignPukIn(List<String> values) {
            addCriterion("auth_sign_puk in", values, "authSignPuk");
            return (Criteria) this;
        }

        public Criteria andAuthSignPukNotIn(List<String> values) {
            addCriterion("auth_sign_puk not in", values, "authSignPuk");
            return (Criteria) this;
        }

        public Criteria andAuthSignPukBetween(String value1, String value2) {
            addCriterion("auth_sign_puk between", value1, value2, "authSignPuk");
            return (Criteria) this;
        }

        public Criteria andAuthSignPukNotBetween(String value1, String value2) {
            addCriterion("auth_sign_puk not between", value1, value2, "authSignPuk");
            return (Criteria) this;
        }

        public Criteria andDecodeSecretKeyIsNull() {
            addCriterion("decode_secret_key is null");
            return (Criteria) this;
        }

        public Criteria andDecodeSecretKeyIsNotNull() {
            addCriterion("decode_secret_key is not null");
            return (Criteria) this;
        }

        public Criteria andDecodeSecretKeyEqualTo(String value) {
            addCriterion("decode_secret_key =", value, "decodeSecretKey");
            return (Criteria) this;
        }

        public Criteria andDecodeSecretKeyNotEqualTo(String value) {
            addCriterion("decode_secret_key <>", value, "decodeSecretKey");
            return (Criteria) this;
        }

        public Criteria andDecodeSecretKeyGreaterThan(String value) {
            addCriterion("decode_secret_key >", value, "decodeSecretKey");
            return (Criteria) this;
        }

        public Criteria andDecodeSecretKeyGreaterThanOrEqualTo(String value) {
            addCriterion("decode_secret_key >=", value, "decodeSecretKey");
            return (Criteria) this;
        }

        public Criteria andDecodeSecretKeyLessThan(String value) {
            addCriterion("decode_secret_key <", value, "decodeSecretKey");
            return (Criteria) this;
        }

        public Criteria andDecodeSecretKeyLessThanOrEqualTo(String value) {
            addCriterion("decode_secret_key <=", value, "decodeSecretKey");
            return (Criteria) this;
        }

        public Criteria andDecodeSecretKeyLike(String value) {
            addCriterion("decode_secret_key like", value, "decodeSecretKey");
            return (Criteria) this;
        }

        public Criteria andDecodeSecretKeyNotLike(String value) {
            addCriterion("decode_secret_key not like", value, "decodeSecretKey");
            return (Criteria) this;
        }

        public Criteria andDecodeSecretKeyIn(List<String> values) {
            addCriterion("decode_secret_key in", values, "decodeSecretKey");
            return (Criteria) this;
        }

        public Criteria andDecodeSecretKeyNotIn(List<String> values) {
            addCriterion("decode_secret_key not in", values, "decodeSecretKey");
            return (Criteria) this;
        }

        public Criteria andDecodeSecretKeyBetween(String value1, String value2) {
            addCriterion("decode_secret_key between", value1, value2, "decodeSecretKey");
            return (Criteria) this;
        }

        public Criteria andDecodeSecretKeyNotBetween(String value1, String value2) {
            addCriterion("decode_secret_key not between", value1, value2, "decodeSecretKey");
            return (Criteria) this;
        }

        public Criteria andSecurityQuestionsIsNull() {
            addCriterion("security_questions is null");
            return (Criteria) this;
        }

        public Criteria andSecurityQuestionsIsNotNull() {
            addCriterion("security_questions is not null");
            return (Criteria) this;
        }

        public Criteria andSecurityQuestionsEqualTo(String value) {
            addCriterion("security_questions =", value, "securityQuestions");
            return (Criteria) this;
        }

        public Criteria andSecurityQuestionsNotEqualTo(String value) {
            addCriterion("security_questions <>", value, "securityQuestions");
            return (Criteria) this;
        }

        public Criteria andSecurityQuestionsGreaterThan(String value) {
            addCriterion("security_questions >", value, "securityQuestions");
            return (Criteria) this;
        }

        public Criteria andSecurityQuestionsGreaterThanOrEqualTo(String value) {
            addCriterion("security_questions >=", value, "securityQuestions");
            return (Criteria) this;
        }

        public Criteria andSecurityQuestionsLessThan(String value) {
            addCriterion("security_questions <", value, "securityQuestions");
            return (Criteria) this;
        }

        public Criteria andSecurityQuestionsLessThanOrEqualTo(String value) {
            addCriterion("security_questions <=", value, "securityQuestions");
            return (Criteria) this;
        }

        public Criteria andSecurityQuestionsLike(String value) {
            addCriterion("security_questions like", value, "securityQuestions");
            return (Criteria) this;
        }

        public Criteria andSecurityQuestionsNotLike(String value) {
            addCriterion("security_questions not like", value, "securityQuestions");
            return (Criteria) this;
        }

        public Criteria andSecurityQuestionsIn(List<String> values) {
            addCriterion("security_questions in", values, "securityQuestions");
            return (Criteria) this;
        }

        public Criteria andSecurityQuestionsNotIn(List<String> values) {
            addCriterion("security_questions not in", values, "securityQuestions");
            return (Criteria) this;
        }

        public Criteria andSecurityQuestionsBetween(String value1, String value2) {
            addCriterion("security_questions between", value1, value2, "securityQuestions");
            return (Criteria) this;
        }

        public Criteria andSecurityQuestionsNotBetween(String value1, String value2) {
            addCriterion("security_questions not between", value1, value2, "securityQuestions");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andMemberIdLikeInsensitive(String value) {
            addCriterion("upper(member_id) like", value.toUpperCase(), "memberId");
            return (Criteria) this;
        }

        public Criteria andPublicKeyLikeInsensitive(String value) {
            addCriterion("upper(public_key) like", value.toUpperCase(), "publicKey");
            return (Criteria) this;
        }

        public Criteria andNickNameLikeInsensitive(String value) {
            addCriterion("upper(nick_name) like", value.toUpperCase(), "nickName");
            return (Criteria) this;
        }

        public Criteria andRoleLikeInsensitive(String value) {
            addCriterion("upper(role) like", value.toUpperCase(), "role");
            return (Criteria) this;
        }

        public Criteria andPasswordLikeInsensitive(String value) {
            addCriterion("upper(password) like", value.toUpperCase(), "password");
            return (Criteria) this;
        }

        public Criteria andBindedDeviceLikeInsensitive(String value) {
            addCriterion("upper(binded_device) like", value.toUpperCase(), "bindedDevice");
            return (Criteria) this;
        }

        public Criteria andAuthSignPukLikeInsensitive(String value) {
            addCriterion("upper(auth_sign_puk) like", value.toUpperCase(), "authSignPuk");
            return (Criteria) this;
        }

        public Criteria andDecodeSecretKeyLikeInsensitive(String value) {
            addCriterion("upper(decode_secret_key) like", value.toUpperCase(), "decodeSecretKey");
            return (Criteria) this;
        }

        public Criteria andSecurityQuestionsLikeInsensitive(String value) {
            addCriterion("upper(security_questions) like", value.toUpperCase(), "securityQuestions");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}