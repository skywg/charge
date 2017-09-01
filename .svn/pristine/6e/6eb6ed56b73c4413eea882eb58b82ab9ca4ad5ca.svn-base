/*
Navicat MySQL Data Transfer

Source Server         : 180.97.90.76
Source Server Version : 50540
Source Host           : 180.97.90.76:3306
Source Database       : htcharger

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2017-02-13 10:37:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `pkey` varchar(255) NOT NULL,
  `p_descr` varchar(255) DEFAULT NULL,
  `p_name` varchar(255) DEFAULT NULL,
  `parent_key` varchar(255) DEFAULT NULL,
  `click_id` varchar(255) DEFAULT NULL,
  `uri` varchar(255) DEFAULT NULL,
  `menu_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`pkey`),
  KEY `FK_4ji5d2xoxa7mheyn57p6ybc70` (`menu_id`),
  CONSTRAINT `FK_4ji5d2xoxa7mheyn57p6ybc70` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('accountDataCount', null, 'accountDataCount', null, null, '/admin/reports/accountDataCount/account/check', '25');
INSERT INTO `permission` VALUES ('accountexport', null, '导出', null, 'accountexport', '/admin/reports/exportaccountDataCount', '35');
INSERT INTO `permission` VALUES ('accounts', '', '/admin/accounts/', '', null, '/admin/accounts/', '7');
INSERT INTO `permission` VALUES ('accountsaccountEdit', null, '/admin/accounts/accountEdit', null, null, '/admin/accounts/accountEdit/*', '8');
INSERT INTO `permission` VALUES ('accountsaccoutAdd', null, '/admin/accounts/accoutAdd', null, null, '/admin/accounts/accoutAdd', '8');
INSERT INTO `permission` VALUES ('accountsaccoutDebit', null, '/admin/accounts/accoutDebit', null, null, '/admin/accounts/accoutDebit/*', '8');
INSERT INTO `permission` VALUES ('accountscheckCar', null, '/admin/accounts/checkCar', null, null, '/admin/accounts/checkCar/*', '8');
INSERT INTO `permission` VALUES ('accountscheckCard', null, '/admin/accounts/checkCard', null, null, '/admin/accounts/checkCard/*', '8');
INSERT INTO `permission` VALUES ('accountscheckCardDetails', null, '/admin/accounts/checkCardDetails', null, null, '/admin/accounts/checkCardDetails', '8');
INSERT INTO `permission` VALUES ('accountscheckCharge', null, '/admin/accounts/checkCharge', null, null, '/admin/accounts/checkCharge/*', '8');
INSERT INTO `permission` VALUES ('accountscheckDeals', null, '/admin/accounts/checkDeal', null, null, '/admin/accounts/checkDeal', '8');
INSERT INTO `permission` VALUES ('accountssearchCharge', null, '/admin/accounts/searchCharge', null, null, '/admin/accounts/searchCharge/*/*', '8');
INSERT INTO `permission` VALUES ('accountssearchDeal', null, '/admin/accounts/searchDeal', null, null, '/admin/accounts/searchDeal/*', '8');
INSERT INTO `permission` VALUES ('accountssearchge', null, '/admin/accounts/searchge', null, null, '/admin/accounts/searchge/*', '8');
INSERT INTO `permission` VALUES ('accountssearchTime', null, '/admin/accounts/searchTime', null, null, '/admin/accounts/searchTime/*', '8');
INSERT INTO `permission` VALUES ('addAccount', null, '添加', null, 'addAccount', '/admin/accounts/add', '8');
INSERT INTO `permission` VALUES ('addCard', null, '添加', null, 'addCard', '/admin/cards/add', '10');
INSERT INTO `permission` VALUES ('addCharger', null, '添加', null, 'addCharger', '/admin/chargers/add', '2');
INSERT INTO `permission` VALUES ('addContent', null, '添加', null, 'addContent', '/admin/contents/add', '19');
INSERT INTO `permission` VALUES ('addeventCode', null, '添加', null, 'addeventCode', '/admin/eventCodes/add', '34');
INSERT INTO `permission` VALUES ('addFeedBackAuditLog', null, '/admin/feedBacks/addFeedBackAuditLog', null, null, '/admin/feedBacks/addFeedBackAuditLog/*', '17');
INSERT INTO `permission` VALUES ('addnotify', null, '添加', null, 'addnotify', '/admin/notify/add', '22');
INSERT INTO `permission` VALUES ('addOperator', null, '添加', null, 'addOperator', '/admin/operators/add', '9');
INSERT INTO `permission` VALUES ('addParamSetting', null, '添加', null, 'addParamSetting', '/admin/paramSettings/add', '6');
INSERT INTO `permission` VALUES ('addPrice', null, '添加', null, 'addPrice', '/admin/paramTemplates/add', '5');
INSERT INTO `permission` VALUES ('addStation', null, '添加', null, 'addStation', '/admin/stations/add', '3');
INSERT INTO `permission` VALUES ('admin', '', '/admin', '', null, '/admin', '1');
INSERT INTO `permission` VALUES ('adminaccountssearchTime', null, '/admin/accounts/searchTime', null, null, '/admin/accounts/searchTime/*/*', '8');
INSERT INTO `permission` VALUES ('adminchargers', null, '/admin/chargers/', null, null, '/admin/chargers/*', '1');
INSERT INTO `permission` VALUES ('adminchargersparam', null, '/admin/chargers/param', null, null, '/admin/chargers/param/*', '1');
INSERT INTO `permission` VALUES ('adminchargersret', null, '/admin/chargers/ret/', null, null, '/admin/chargers/ret/*', '1');
INSERT INTO `permission` VALUES ('admincheckchargers', null, '/admin/stations/check/chargers', null, null, '/admin/stations/check/chargers/*', '1');
INSERT INTO `permission` VALUES ('admineventCodes', null, '/admin/eventCodes/', null, null, '/admin/eventCodes/', '15');
INSERT INTO `permission` VALUES ('adminevents', null, '/admin/events', null, null, '/admin/events/*', '15');
INSERT INTO `permission` VALUES ('adminfeedBacks', null, '/admin/feedBacks/', null, null, '/admin/feedBacks/', '17');
INSERT INTO `permission` VALUES ('adminfeedBackshandle', null, '/admin/feedBacks/handle/*', null, null, '/admin/feedBacks/handle/*', '17');
INSERT INTO `permission` VALUES ('adminfeedBacksresearch', null, '/admin/feedBacks/research', null, null, '/admin/feedBacks/research', '17');
INSERT INTO `permission` VALUES ('adminfeedBacksupdate', null, '/admin/feedBacks/update', null, null, '/admin/feedBacks/update/*', '17');
INSERT INTO `permission` VALUES ('adminmanagers', null, '/admin/managers/', null, null, '/admin/managers/', '28');
INSERT INTO `permission` VALUES ('adminmanagersadd', null, '/admin/managers/add', null, null, '/admin/managers/add', '28');
INSERT INTO `permission` VALUES ('adminmanagerssearch', null, '/admin/managers/search', null, null, '/admin/managers/search', '28');
INSERT INTO `permission` VALUES ('adminnotify', null, '/admin/notify', null, null, '/admin/notify/', '18');
INSERT INTO `permission` VALUES ('adminnotifyaddnotify', null, '/admin/notify/addnotify', null, null, '/admin/notify/addnotify/', '18');
INSERT INTO `permission` VALUES ('adminnotifyajaxmailcontent', null, '/admin/notify/ajax_mail_content', null, null, '/admin/notify/ajax_mail_content', '18');
INSERT INTO `permission` VALUES ('adminnotifyajaxsearch', null, '/admin/notify/ajax_search', null, null, '/admin/notify/ajax_search', '18');
INSERT INTO `permission` VALUES ('adminnotifysearchajax', null, '/admin/notify/search_ajax', null, null, '/admin/notify/search_ajax', '18');
INSERT INTO `permission` VALUES ('adminnotifysendOne', null, '/admin/notify/sendOne', null, null, '/admin/notify/sendOne/*', '18');
INSERT INTO `permission` VALUES ('adminnotifyupdate', null, '/admin/notify/update', null, null, '/admin/notify/update/*', '18');
INSERT INTO `permission` VALUES ('adminoperato', null, '/admin/operators', null, null, '/admin/operators/*', '9');
INSERT INTO `permission` VALUES ('adminoperators', null, '/admin/operators', null, null, '/admin/operators/*/chargers/', '9');
INSERT INTO `permission` VALUES ('adminoperatorsstations', null, '/admin/operators/stations/', null, null, '/admin/operators/*/stations/', '9');
INSERT INTO `permission` VALUES ('adminparamSettings', null, '/admin/paramSettings/*', null, null, '/admin/paramSettings/*', '6');
INSERT INTO `permission` VALUES ('adminparamTemplates', null, '/admin/paramTemplates/', null, null, '/admin/paramTemplates/', '4');
INSERT INTO `permission` VALUES ('adminreportsuserCount', null, '/admin/reports/userCount', null, null, '/admin/reports/userCount', '23');
INSERT INTO `permission` VALUES ('adminreviewsresearch', null, '/admin/reviews/research/', null, null, '/admin/reviews/research', '1');
INSERT INTO `permission` VALUES ('adminstations', null, '/admin/stations/*', null, null, '/admin/stations/*', '3');
INSERT INTO `permission` VALUES ('adminstationsadd', null, '/admin/stations/add/', null, null, '/admin/stations/add/*', '1');
INSERT INTO `permission` VALUES ('adminstationsimages', null, '/admin/stations/images', null, null, '/admin/stations/images/*', '1');
INSERT INTO `permission` VALUES ('adminsystemedit', null, '/admin/system/edit', null, null, '/admin/system/roles/edit/*', '28');
INSERT INTO `permission` VALUES ('adminsystemroles', null, '/admin/system/roles/', null, null, '/admin/system/roles/', '28');
INSERT INTO `permission` VALUES ('batchCard', null, '批量划账', null, 'batchCard', '/admin/cards/toBatchCharging', '10');
INSERT INTO `permission` VALUES ('buttonId', '', '/admin/menu/buttonId', '', null, '/admin/menu/buttonId', '1');
INSERT INTO `permission` VALUES ('cancelCard', null, '销卡', null, 'cancelCard', '/admin/cards/cancelled', '10');
INSERT INTO `permission` VALUES ('car', '', '/admin/car/', '', null, '/admin/car/', '11');
INSERT INTO `permission` VALUES ('cards', '', '/admin/cards/', '', null, '/admin/cards/', '10');
INSERT INTO `permission` VALUES ('cardsaddcard', null, '/admin/cards/addcard', null, null, '/admin/cards/addcard', '10');
INSERT INTO `permission` VALUES ('cardsbatchCharging', null, '/admin/cards/batchCharging', null, null, '/admin/cards/batchCharging', '10');
INSERT INTO `permission` VALUES ('cardscharging', null, '/admin/cards/charging', null, null, '/admin/cards/charging', '10');
INSERT INTO `permission` VALUES ('cardscheckDetailed', null, '/admin/cards/checkDetailed', null, null, '/admin/cards/checkDetailed/*', '10');
INSERT INTO `permission` VALUES ('cardsupdate', null, '/admin/cards/update', null, null, '/admin/cards/update/*', '10');
INSERT INTO `permission` VALUES ('carsave', null, '/admin/car/save', null, null, '/admin/car/save/*', '11');
INSERT INTO `permission` VALUES ('chargeRecordSearch', null, '/admin/cards/chargeRecordSearch', null, null, '/admin/cards/chargeRecordSearch', '10');
INSERT INTO `permission` VALUES ('chargers', '', '/admin/chargers/', '', null, '/admin/chargers/', '2');
INSERT INTO `permission` VALUES ('chargerscheck1', null, '/admin/chargers/check1', null, null, '/admin/chargers/check1/*/*', '1');
INSERT INTO `permission` VALUES ('chargerscheckDeal', null, '/admin/chargers/checkDeal', null, null, '/admin/chargers/checkDeal/*', '1');
INSERT INTO `permission` VALUES ('chargersdels', null, '/admin/chargers/dels', null, null, '/admin/chargers/dels/*/*', '1');
INSERT INTO `permission` VALUES ('chargersret', null, '/admin/chargers/ret', null, '', '/admin/chargers/ret/*', '1');
INSERT INTO `permission` VALUES ('chargerssearchDeal', null, '/admin/chargers/searchDeal/', null, null, '/admin/chargers/searchDeal/*', '1');
INSERT INTO `permission` VALUES ('chargerssearchTime', null, '/admin/chargers/searchTime/', null, null, '/admin/chargers/searchTime/*/*', '1');
INSERT INTO `permission` VALUES ('checkAccount', null, '查看', null, 'checkAccount', '/admin/accounts/*', '8');
INSERT INTO `permission` VALUES ('checkCar', null, '查看', null, 'checkCar', '/admin/car/check/*', '11');
INSERT INTO `permission` VALUES ('checkCard', null, '查看', null, 'checkCard', '/admin/cards/checkDetailed/*', '10');
INSERT INTO `permission` VALUES ('checkCharger', null, '查看', null, 'checkCharger', '/admin/chargers/check/*', '2');
INSERT INTO `permission` VALUES ('checkContent', null, '编辑查看', null, 'checkContent', '/admin/contents/check/*', '19');
INSERT INTO `permission` VALUES ('checkeventCode', null, '查看', null, 'checkeventCode', '/admin/eventCodes/check/*', '34');
INSERT INTO `permission` VALUES ('checkevents', null, '查看', null, 'checkevents', '/admin/events/check/*', '16');
INSERT INTO `permission` VALUES ('checkfeedBack', null, '查看', null, 'checkfeedBack', '/admin/feedBacks/check/*', '21');
INSERT INTO `permission` VALUES ('checknotify', null, '查看', null, 'checknotify', '/admin/notify/check/*', '22');
INSERT INTO `permission` VALUES ('checkOperator', null, '查看', null, 'checkOperator', '/admin/operators/check/*', '9');
INSERT INTO `permission` VALUES ('checkOrder', null, '查看', null, 'checkOrder', '/admin/orders/*', '13');
INSERT INTO `permission` VALUES ('checkParamSetting', null, '查看', null, 'checkParamSetting', '/admin/paramSettings/check/*', '6');
INSERT INTO `permission` VALUES ('checkPrice', null, '查看', null, 'checkPrice', '/admin/paramTemplates/check/*', '5');
INSERT INTO `permission` VALUES ('checkStation', null, '查看', null, 'checkStation', '/admin/stations/check/*', '3');
INSERT INTO `permission` VALUES ('contents', null, '/admin/contents/', '', null, '/admin/contents/', '18');
INSERT INTO `permission` VALUES ('contentsaddcontent', null, '/admin/contents/addcontent', null, null, '/admin/contents/addcontent', '18');
INSERT INTO `permission` VALUES ('contentsupdate', null, '/admin/contents/update/', null, null, '/admin/contents/update/*', '18');
INSERT INTO `permission` VALUES ('debitAccount', null, '充值', null, 'debitAccount', '/admin/accounts/debit/*', '8');
INSERT INTO `permission` VALUES ('delCharger', null, '删除', null, 'delCharger', '/admin/chargers/del/*', '2');
INSERT INTO `permission` VALUES ('delOperator', null, '删除', null, 'delOperator', '/admin/operators/del/*', '9');
INSERT INTO `permission` VALUES ('delParamSetting', null, '删除', null, 'delParamSetting', '/admin/paramSettings/del/*', '6');
INSERT INTO `permission` VALUES ('delPrice', null, '删除', null, 'delPrice', '/admin/paramTemplates/del/*', '5');
INSERT INTO `permission` VALUES ('delStation', null, '删除', null, 'delStation', '/admin/stations/del/*', '3');
INSERT INTO `permission` VALUES ('dminaccocheckDeal', null, '/admin/accounts/checkDeal/', null, null, '/admin/accounts/checkDeal/*', '8');
INSERT INTO `permission` VALUES ('editAccount', null, '编辑', null, 'editAccount', '/admin/accounts/edit/*', '8');
INSERT INTO `permission` VALUES ('editCard', null, '编辑', null, 'editCard', '/admin/cards/edit/*', '10');
INSERT INTO `permission` VALUES ('editCharger', null, '编辑', null, 'editCharger', '/admin/chargers/edit/*', '2');
INSERT INTO `permission` VALUES ('editContent', null, '编辑', null, 'editContent', '/admin/contents/edit/*', '19');
INSERT INTO `permission` VALUES ('editeventCode', null, '编辑', null, 'editeventCode', '/admin/eventCodes/edit/*', '34');
INSERT INTO `permission` VALUES ('editfeedBack', null, '编辑', null, 'editfeedBack', '/admin/feedBacks/edit/*', '21');
INSERT INTO `permission` VALUES ('editnotify', null, '编辑', null, 'editnotify', '/admin/notify/edit/*', '22');
INSERT INTO `permission` VALUES ('editOperator', null, '编辑', null, 'editOperator', '/admin/operators/edit/*', '9');
INSERT INTO `permission` VALUES ('editParamSetting', null, '编辑', null, 'editParamSetting', '/admin/paramSettings/edit/*', '6');
INSERT INTO `permission` VALUES ('editPrice', null, '编辑', null, 'editPrice', '/admin/paramTemplates/edit/*', '5');
INSERT INTO `permission` VALUES ('editStation', null, '编辑', null, 'editStation', '/admin/stations/edit/*', '3');
INSERT INTO `permission` VALUES ('eventCodesaddEventCode', null, '/admin/eventCodes/addEventCode', null, null, '/admin/eventCodes/addEventCode', '15');
INSERT INTO `permission` VALUES ('eventCodesupdate', null, '/admin/eventCodes/update', null, null, '/admin/eventCodes/update/*', '15');
INSERT INTO `permission` VALUES ('events', '', '/admin/events/', '', null, '/admin/events/', '15');
INSERT INTO `permission` VALUES ('eventsaddeventAuditLog', null, '/admin/events/addeventAuditLog', null, null, '/admin/events/addeventAuditLog/*', '15');
INSERT INTO `permission` VALUES ('examineContent', null, '审核', null, 'examineContent', '/admin/contents/review/*', '19');
INSERT INTO `permission` VALUES ('exportAccount', null, '导出', null, 'exportAccount', '/admin/accounts/excle_output', '8');
INSERT INTO `permission` VALUES ('exportCard', null, '导出', null, 'exportCard', '/admin/cards/excle_output', '10');
INSERT INTO `permission` VALUES ('exportCharger', null, '导出', null, 'exportCharger', '/admin/chargers/excle_output', '2');
INSERT INTO `permission` VALUES ('exportEvent', null, '导出', null, 'exportEvent', '/admin/events/excle_output', '16');
INSERT INTO `permission` VALUES ('exportOrder', null, '导出', null, 'exportOrder', '/admin/orders/excle_output', '13');
INSERT INTO `permission` VALUES ('exportStation', null, '导出', null, 'exportStation', '/admin/stations/excle_output', '3');
INSERT INTO `permission` VALUES ('feedBacksaddFeedBackAudit', null, '/admin/feedBacks/addFeedBackAuditLog/', null, null, '/admin/feedBacks/addFeedBackAuditLog/', '17');
INSERT INTO `permission` VALUES ('handleevents', null, '处理', null, 'handleevents', '/admin/events/handle/*', '16');
INSERT INTO `permission` VALUES ('identifyCar', null, '处理', null, 'identifyCar', '/admin/car/deal/*', '11');
INSERT INTO `permission` VALUES ('lockContent', null, '发布查看', null, 'lockContent', '/admin/contents/check/*', '19');
INSERT INTO `permission` VALUES ('lossCard', null, '挂失', null, 'lossCard', '/admin/cards/loss', '10');
INSERT INTO `permission` VALUES ('managefeedBack', null, '处理', null, 'managefeedBack', '/admin/feedBacks/handle/*', '21');
INSERT INTO `permission` VALUES ('managerscheck', null, '/admin/managers/check', null, null, '/admin/managers/check/*', '28');
INSERT INTO `permission` VALUES ('managersdel', null, '/admin/managers/del', null, null, '/admin/managers/del/*', '28');
INSERT INTO `permission` VALUES ('managersed', null, '/admin/managers/ed', null, null, '/admin/managers/ed/*', '28');
INSERT INTO `permission` VALUES ('managersedit', null, '/admin/managers/edit/', null, null, '/admin/managers/edit/*', '28');
INSERT INTO `permission` VALUES ('managerseditPass', null, '/admin/managers/editPass', null, null, '/admin/managers/editPass', '28');
INSERT INTO `permission` VALUES ('managerseditStatus', null, '/admin/managers/editStatus', null, null, '/admin/managers/editStatus/*', '28');
INSERT INTO `permission` VALUES ('managersexit', null, '/admin/managers/exit', null, null, '/admin/managers/exit', '28');
INSERT INTO `permission` VALUES ('managersloadRole', null, '/admin/managers/loadRole', null, null, '/admin/managers/loadRole/*', '28');
INSERT INTO `permission` VALUES ('managersmanagerAdd', null, '/admin/managers/managerAdd', null, null, '/admin/managers/managerAdd', '28');
INSERT INTO `permission` VALUES ('managersmodifyPwd', null, '/admin/managers/modifyPwd', null, null, '/admin/managers/modifyPwd', '28');
INSERT INTO `permission` VALUES ('managersresetPassword', null, '/admin/managers/resetPassword', null, null, '/admin/managers/resetPassword', '28');
INSERT INTO `permission` VALUES ('managerTree', '', '/admin/menu/managerTree', '', null, '/admin/menu/managerTree', '1');
INSERT INTO `permission` VALUES ('menuData', '', '/admin/menu/menuData', '', null, '/admin/menu/menuData', '1');
INSERT INTO `permission` VALUES ('meunTree', '', '/admin/menu/meunTree', '', null, '/admin/menu/meunTree', '1');
INSERT INTO `permission` VALUES ('noReview', null, '审核不通过', null, 'noReview', '/admin/reviews/verifyN/*', '14');
INSERT INTO `permission` VALUES ('operators', '', '/admin/operators/', '', null, '/admin/operators/', '9');
INSERT INTO `permission` VALUES ('orders', '', '/admin/orders/', '', null, '/admin/orders/', '13');
INSERT INTO `permission` VALUES ('paramparamTemplate', null, '/admin/paramSettings/paramTemplate', null, null, '/admin/paramSettings/paramTemplate/*', '6');
INSERT INTO `permission` VALUES ('paramSettingparamTemplate', null, '/admin/paramSettings/paramTemplate', null, null, '/admin/paramSettings/paramTemplate', '6');
INSERT INTO `permission` VALUES ('paramSettings', '', '/admin/paramSettings/', '', null, '/admin/paramSettings/', '6');
INSERT INTO `permission` VALUES ('paramSettingssendss', null, '/admin/paramSettings/send', null, null, '/admin/paramSettings/send/*/*/*/*/*', '6');
INSERT INTO `permission` VALUES ('paramTemplatesaddTemplate', null, '/admin/paramTemplates/addTemplate', null, null, '/admin/paramTemplates/addTemplate', '4');
INSERT INTO `permission` VALUES ('paramTemplatessave', null, '/admin/paramTemplates/save', null, null, '/admin/paramTemplates/save/*', '4');
INSERT INTO `permission` VALUES ('permissionTree', '', '/admin/menu/permissionTree', '', null, '/admin/menu/permissionTree', '1');
INSERT INTO `permission` VALUES ('publishContent', null, '发布', null, 'publishContent', '/admin/contents/release/*', '19');
INSERT INTO `permission` VALUES ('recharge', null, '划账', null, 'recharge', '/admin/cards/toCharging', '10');
INSERT INTO `permission` VALUES ('rechargeRecordSearch', null, '/admin/cards/rechargeRecordSearch', null, null, '/admin/cards/rechargeRecordSearch', '10');
INSERT INTO `permission` VALUES ('reportsaccountDataCount', null, '/admin/reports/accountDataCount', null, null, '/admin/reports/accountDataCount', '23');
INSERT INTO `permission` VALUES ('reportschargingDataCount', null, '/admin/reports/chargingDataCount', null, null, '/admin/reports/chargingDataCount', '23');
INSERT INTO `permission` VALUES ('reportseventDataCount', null, '/admin/reports/eventDataCount', null, null, '/admin/reports/eventDataCount', '23');
INSERT INTO `permission` VALUES ('reportsfindListAllStation', null, '/admin/reports/findListAllStation', null, null, '/admin/reports/findListAllStation', '25');
INSERT INTO `permission` VALUES ('reportsrecordDataCount', null, '/admin/reports/recordDataCount', null, null, '/admin/reports/recordDataCount', '23');
INSERT INTO `permission` VALUES ('reviewContent', null, '回复', null, 'reviewContent', '/admin/reviews/addReview', '14');
INSERT INTO `permission` VALUES ('reviews', '', '/admin/reviews/', '', null, '/admin/reviews/', '14');
INSERT INTO `permission` VALUES ('reviewsaddReview', null, '/admin/reviews/addReview', null, null, '/admin/reviews/addReview', '14');
INSERT INTO `permission` VALUES ('searchAccount', null, '查询', null, 'searchAccount', '/admin/accounts/search/', '8');
INSERT INTO `permission` VALUES ('searchaccountDataCount', null, '查询', null, 'searchaccountDataCount', '/admin/reports/accountDataCount/search', '35');
INSERT INTO `permission` VALUES ('searchCar', null, '查询', null, 'searchCar', '/admin/car/search', '11');
INSERT INTO `permission` VALUES ('searchCard', null, '查询', null, 'searchCard', '/admin/cards/search/', '10');
INSERT INTO `permission` VALUES ('searchCharger', null, '查询', '', 'searchCharger', '/admin/chargers/search/', '2');
INSERT INTO `permission` VALUES ('searchchargingDataCount', null, '查询', null, 'searchchargingDataCount', '/admin/reports/chargingDataCount', '25');
INSERT INTO `permission` VALUES ('searchContent', null, '查询', null, 'searchContent', '/admin/contents/search', '19');
INSERT INTO `permission` VALUES ('searchEvent', null, '查询', null, 'searchEvent', '/admin/events/query', '16');
INSERT INTO `permission` VALUES ('searcheventCode', null, '查询', null, 'searcheventCode', '/admin/eventCodes/query', '34');
INSERT INTO `permission` VALUES ('searcheventDataCount', null, '查询', null, 'searcheventDataCount', '/admin/reports/eventDataCount', '26');
INSERT INTO `permission` VALUES ('searchfeedBack', null, '查询', null, 'searchfeedBack', '/admin/feedBacks/research', '21');
INSERT INTO `permission` VALUES ('searchnotify', null, '查询', null, 'searchnotify', '/admin/notify/search', '22');
INSERT INTO `permission` VALUES ('searchOperator', null, '查询', null, 'searchOperator', '/admin/operators/search/', '9');
INSERT INTO `permission` VALUES ('searchOrder', null, '查询', null, 'searchOrder', '/admin/orders/search', '13');
INSERT INTO `permission` VALUES ('searchParamSetting', null, '查询', null, 'searchParamSetting', '/admin/paramSettings/search/', '6');
INSERT INTO `permission` VALUES ('searchPrice', null, '查询', null, 'searchPrice', '/admin/paramTemplates/query', '5');
INSERT INTO `permission` VALUES ('searchrecordDataCount', null, '查询', null, 'searchrecordDataCount', '/admin/reports/recordDataCount', '27');
INSERT INTO `permission` VALUES ('searchReview', null, '查询', null, 'searchReview', '/admin/reviews/research/', '14');
INSERT INTO `permission` VALUES ('searchStation', null, '查询', null, 'searchStation', '/admin/stations/search/', '3');
INSERT INTO `permission` VALUES ('searchuserCount', null, '查询', null, 'searchuserCount', '/admin/reports/userCount', '24');
INSERT INTO `permission` VALUES ('sendnotify', null, '发送', null, 'sendnotify', '/admin/notify/send/*', '22');
INSERT INTO `permission` VALUES ('sendParamSetting', null, '下发', null, 'sendParamSetting', '/admin/paramSettings/sendParam/*', '6');
INSERT INTO `permission` VALUES ('shelfContent', null, '下架', null, 'shelfContent', '/admin/contents/drop/*', '19');
INSERT INTO `permission` VALUES ('startCard', null, '启用/禁用', null, 'startCard', '/admin/cards/start', '10');
INSERT INTO `permission` VALUES ('stations', '', '/admin/stations/', '', null, '/admin/stations/', '3');
INSERT INTO `permission` VALUES ('stationsadds', null, '/admin/stations/adds/', null, null, '/admin/stations/adds/*', '1');
INSERT INTO `permission` VALUES ('stationscheckimages', null, '/admin/stations/check/images', null, null, '/admin/stations/check/images/*', '1');
INSERT INTO `permission` VALUES ('stationscheckreviews', null, '/admin/stations/check/reviews', null, null, '/admin/stations/check/reviews/*', '1');
INSERT INTO `permission` VALUES ('stationseditchargers', null, '/admin/stations/edit/chargers', null, null, '/admin/stations/edit/chargers/*', '1');
INSERT INTO `permission` VALUES ('stationseditimages', null, '/admin/stations/edit/images', null, null, '/admin/stations/edit/images/*', '1');
INSERT INTO `permission` VALUES ('submitContent', null, '提交', null, 'submitContent', '/admin/contents/submit/*', '19');
INSERT INTO `permission` VALUES ('systemroles', null, '/admin/system/roles/*', null, null, '/admin/system/roles/*', '28');
INSERT INTO `permission` VALUES ('systemroles1', null, '/admin/system/roles/', null, null, '/admin/system/roles/', '28');
INSERT INTO `permission` VALUES ('systemversion', null, '/admin/system/version/', null, null, '/admin/system/version/', '28');
INSERT INTO `permission` VALUES ('systemversionadd', null, '/admin/system/version/add', null, null, '/admin/system/version/add', '28');
INSERT INTO `permission` VALUES ('systemversionedit', null, '/admin/system/version/edit', null, null, '/admin/system/version/edit/*', '28');
INSERT INTO `permission` VALUES ('systemversionsearch', null, '/admin/system/version/search', null, null, '/admin/system/version/search', '28');
INSERT INTO `permission` VALUES ('tationsmages', null, '/admin/stations/images', null, null, '/admin/stations/images/*', '1');
INSERT INTO `permission` VALUES ('timages', null, '/admin/stations/images', null, null, '/admin/stations/images', '1');
INSERT INTO `permission` VALUES ('toBatchChargingajax', null, '/admin/cards/toBatchCharging_ajax', null, null, '/admin/cards/toBatchCharging_ajax', '10');
INSERT INTO `permission` VALUES ('userexport', null, '导出', null, 'userexport', '/admin/reports/exportuserCount', '24');
INSERT INTO `permission` VALUES ('versionaddversion', null, '/admin/system/version/addversion', null, null, '/admin/system/version/addversion', '28');
INSERT INTO `permission` VALUES ('versioncheck', null, '/admin/system/version/check/', null, null, '/admin/system/version/check/*', '28');
INSERT INTO `permission` VALUES ('versionupdate', null, '/admin/system/version/update', null, null, '/admin/system/version/update/*', '28');
INSERT INTO `permission` VALUES ('versionupgrade', null, '/admin/system/version/upgrade', null, null, '/admin/system/version/upgrade/*', '28');
INSERT INTO `permission` VALUES ('yalarmexport', null, '导出', null, 'alarmexport', '/admin/reports/exporteventDataCount', '26');
INSERT INTO `permission` VALUES ('ychargingexport', null, '导出', null, 'chargingexport', '/admin/reports/exportchargingDataCount', '25');
INSERT INTO `permission` VALUES ('ydelaccount', null, '删除', null, 'ydelaccount', '/admin/accounts/del/*', '8');
INSERT INTO `permission` VALUES ('yesnoAccount', null, '启用/禁用', null, 'yesnoAccount', '/admin/accounts/changeStatus/*/*', '8');
INSERT INTO `permission` VALUES ('yesReview', null, '审核通过', null, 'yesReview', '/admin/reviews/verifyY/*', '14');
INSERT INTO `permission` VALUES ('yrecordexport', null, '导出', null, 'recordexport', '/admin/reports/exportrecordDataCount', '27');
