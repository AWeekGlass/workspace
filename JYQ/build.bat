(
	mvn clean package -Dmaven.test.skip=true
	xcopy D:\workspace\JYQ\auth-center\target\auth-center.jar D:\workspace\JYQ\jars\ /y
	xcopy D:\workspace\JYQ\contract-center\target\contract-center.jar D:\workspace\JYQ\jars\ /y
	xcopy D:\workspace\JYQ\exam-center\target\exam-center.jar D:\workspace\JYQ\jars\ /y
	xcopy D:\workspace\JYQ\gateway\target\gateway.jar D:\workspace\JYQ\jars\ /y
	xcopy D:\workspace\JYQ\system-center\target\system-center.jar D:\workspace\JYQ\jars\ /y
	xcopy D:\workspace\JYQ\user-center\target\user-center.jar D:\workspace\JYQ\jars\ /y
	xcopy D:\workspace\JYQ\case-center\target\case-center.jar D:\workspace\JYQ\jars\ /y
	xcopy D:\workspace\JYQ\training-center\target\training-center.jar D:\workspace\JYQ\jars\ /y
	pause
)