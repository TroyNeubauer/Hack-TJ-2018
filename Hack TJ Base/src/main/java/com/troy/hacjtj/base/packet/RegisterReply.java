package com.troy.hacjtj.base.packet;

public class RegisterReply extends PacketData {

	private RegisterReplyEnum reply;

	public RegisterReply(RegisterReplyEnum reply) {
		this.reply = reply;
	}

	public enum RegisterReplyEnum {
		REGISTER_SUCEED(true, "Registration succeeded! You can login now"),
        REGISTER_FAIL_EMAIL_IN_USE(false, "That email is already in use. Try forgot password if you can't remember your old account"),
        REGISTER_FAIL_USERNAME_IN_USE(false, "That username is already in use. Pick a different one");
        RegisterReplyEnum(boolean succeed, String message) {
            this.succeed = succeed;
            this.message = message;
        }

        private boolean succeed;
        private String message;

        public boolean isSucceed() {
            return succeed;
        }

        public String getMessage() {
            return message;
        }
    }

    public boolean isSucceed() {
        return reply.succeed;
    }

    public String getMessage() {
        return reply.message;
    }
}
