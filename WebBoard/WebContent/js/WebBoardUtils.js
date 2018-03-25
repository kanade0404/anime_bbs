// 空欄チェック
function checkBrank(formId, feildId, feildName) {

	// 引数からフォームIDと入力欄IDを受け取り、入力値を取得
	var feildValue = document.forms[formId].elements[feildId].value;

	// 入力値が空欄であれば警告メッセージを表示して保存を中止
	if (feildValue == "") {
		alert(feildName + "を入力して下さい。");
		return false;
	}
}

// 投稿のモード切り替え（編集・削除）
function changeEditMode(onMode, offMode) {
	// 1番目に渡されたidを表示状態にする
	document.getElementById(onMode).style.display = "inline";
	// 2番目に渡されたidを非表示状態にする
	document.getElementById(offMode).style.display = "none";
}

// 編集モード時に、内容が編集されているかチェック
function checkEdited(formId) {

	var oldValue = document.forms[formId].elements["MESSAGE"].value;
	var EditedValue = document.forms[formId].elements["OLD_VALUE"].value;

	if (oldValue == EditedValue) {
		alert("未編集のため保存できません。");
		return false;
	}
}
