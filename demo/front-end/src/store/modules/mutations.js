import getDate from "./../../assets/common/getDate";
import axios from 'axios';
import storage from "@/store/modules/storage";

// 아이템 하나 추가
const addOneItem = async (state, todoItem) => {
    /* 서버 통신 */
    var jsonValue = {
        item: todoItem,
        date: `${getDate().date} ${getDate().week}`,
        time: getDate().time,
        completed: false
    }

    await axios
        .post('/todos/save', JSON.stringify(jsonValue))
        .then(res => {
            if(res.data == "ok"){
                storage.fetch(state.todoOldestOrder);
            } else {
                alert("등록 실패");
            }
        });
}
// 아이템 하나 삭제
const removeOneItem = (state, payload) => {
    /* 서버 통신 */
    axios
        .put('/todos/delete/' + payload.todoItem.id)
        .then(res => {
            if(res.data == "ok"){
                storage.fetch(state.todoOldestOrder);
            } else {
                alert("삭제 실패");
            }
        });

    // localStorage.removeItem(payload.todoItem.item);
    // state.todoItems.splice(payload.index, 1);
}
// 아이템 하나 완료 토글
const toggleOneItem = (state, payload) => {
    /* 서버 통신 */
    var jsonValue = {
        id: payload.todoItem.id,
        completed: !payload.todoItem.completed
    }

    axios
        .put('/todos/' + payload.todoItem.id, JSON.stringify(jsonValue))
        .then(res => {
           if(res.data == "ok"){
               storage.fetch(state.todoOldestOrder);
           } else {
               alert("업데이트 실패");
           }
        });

    // payload.todoItem.completed = !payload.todoItem.completed;
    // localStorage.setItem(payload.todoItem.item, JSON.stringify(payload.todoItem));
}
// 모든 아이템 삭제
const clearAllItem = (state) => {
    var todoItems = state.todoItems;
    if(todoItems.length > 0){
        axios
            .put('/todos/clear')
            .then(res => {
                if(res.data == "ok"){
                    storage.fetch(state.todoOldestOrder);
                } else {
                    alert("클리어 실패");
                }
            });
    }
}
// 최신순 정렬
const sortTodoLatest = (state) => {
    state.todoOldestOrder = false;

    storage.fetch(state.todoOldestOrder);
}
// 오래된 순 정렬
const sortTodoOldest = (state) => {
    state.todoOldestOrder = true;

    storage.fetch(state.todoOldestOrder);
}
// 사용자 이름 추가
const setUserName = (state, userName) => {
    localStorage.setItem("userName", userName);
    state.userName = userName;
}

export { addOneItem, removeOneItem, toggleOneItem, clearAllItem, sortTodoLatest, sortTodoOldest, setUserName };