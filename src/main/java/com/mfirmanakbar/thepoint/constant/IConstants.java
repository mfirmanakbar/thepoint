package com.mfirmanakbar.thepoint.constant;

public interface IConstants {

    interface RABBITMQ {
        interface KEY {
            String SAVE = "save";
            String UPDATE = "update";
        }

        interface EXCHANGE {
            String X_POINT = "x.point";
        }

        interface QUEUE {
            String Q_POINT_SAVE = "q.point.save";
            String Q_POINT_UPDATE = "q.point.update";
        }
    }
}
