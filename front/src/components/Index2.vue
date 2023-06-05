<template>
  <el-container>
    <!--标题头部-->
    <el-header>
      <div class="header-title">可靠节点部署</div>
      <div class="btn-ul">
        <div class="header-left" style="margin-right: 50%">
          <div class="header-button active">结点部署</div>
        </div>
        <div class="header-right">
          <div class="header-button">数字蛮生</div>
        </div>
      </div>
    </el-header>
    <!---主体UI-->
    <el-main  style="position: relative;">
      <!--隧道UI， 拉伸布局-->
      <div class="box" style="padding: 118px 220px; overflow: auto">
        <div class="channel" style="width: 3000px;height: 350px;border: 1px solid #4b6dfe;position: relative;">
          <!--线-->
          <div class="line" style="width: 100%;height: 100%;display: flex;flex-direction: column;justify-content: space-between;">
            <div class="top-line" style="height: 10px; border-bottom: 1px solid #4b6dfe"></div>
            <div class="middle-line" style="border-bottom: 5px dashed #ffff"></div>
            <div class="bottom-line" style="height: 10px; border-top: 1px solid #4b6dfe" ></div>
          </div>
          <!--图标-->
          <div style="position: absolute; left: 0; top: 0">
            <!--方向图标-->
            <div style="position: absolute" v-for="index in 6" :style="{
                left: `${100 + ((3000 - 180) / 5) * (index - 1)}px`,
                top: '120px',
              }" :key="index" >
              <el-tooltip placement="top">
                  <template #content>通行车道指示灯<br/>{{ `(${100 + ((3000 - 180) / 5) * (index - 1)},120)` }}</template>
                  <img src="../assets/icon1.png" style="transform: rotateY(180deg)" />
              </el-tooltip>
            </div>
            <div style="position: absolute" v-for="index in 6" :style="{
                left: `${100 + ((3000 - 180) / 5) * (index - 1)}px`,
                top: '200px',
              }" :key="index" >
              <el-tooltip placement="top">
                  <template #content>通行车道指示灯<br/>{{ `(${100 + ((3000 - 180) / 5) * (index - 1)},200)` }}</template>
                  <img src="../assets/icon1.png" style="transform: rotateY(180deg)" />
              </el-tooltip>
            </div>
            <!--摄像头图标-->
            <div style="position: absolute" v-for="index in 12" :style="{
                left: `${120 + ((3000 - 180) / 11) * (index - 1)}px`,
                top: '-40px',
              }" :key="index" >
              <el-tooltip placement="top">
                  <template #content>摄像头<br/>{{ `(${120 + ((3000 - 180) / 11) * (index - 1)},0)` }}</template>
                  <img src="../assets/camera.png" style="transform: rotateY(180deg); width: 30px;height: 30px;" />
              </el-tooltip>
            </div>
            <!--风机图标-->
            <div style="position: absolute" v-for="index in 2" :style="{
                left: `${300 + (3000 - 300*2)*(index - 1)}px`,
                top: '120px',
              }" :key="index" >
              <el-tooltip placement="top">
                  <template #content>风机<br/>{{ `(${300 + (3000 - 300*2)*(index - 1)},120)` }}</template>
                  <img src="../assets/fengshan.png" style="width: 30px;height: 30px;" />
              </el-tooltip>
            </div>
          </div>
          <!--画点-->
          <div style="position: absolute; left: 0; top: 0">
            <div style="position: absolute" v-for="(item, index) in nodes" :style="{
                left: handleNodeX(item)+'px', top: handleNodeY(item)+'px', transform: 'translate(-50%, -50%)'}" :key="index" >
                <el-tooltip placement="top">
                  <template #content>坐标位置<br/>{{ `(${handleNodeX(item)},${handleNodeY(item)})` }}</template>
                  <template v-if="item.number === '0'">
                    <div :style="{color: 'yellow'}"><span class="iconfont" style="font-size: 25px;">&#xe647;</span></div>
                  </template>
                  <template v-else-if="item.number != '0' && item.state == 'ACTIVE'">
                      <img src="../assets/node-active.png" style="width: 20px;height: 20px;"/>
                  </template>
                  <template v-else-if="item.number != '0' && item.state == 'RELAY'">
                      <img src="../assets/node-relay.png" style="width: 20px;height: 20px;" />
                  </template>
                </el-tooltip>
            </div>
          </div>
          <!--画图例-->
          <div class="case" style="position: absolute;left: -190px; top:0px;">
              <div style="width: 160px; display: flex;justify-content: space-between; color: #ffff;align-items: center;margin-bottom: 25px;" 
                  v-for="(item,index) in iconConfig" :key="index">
                <img :src="getImage(item.path)" style="width:25px;" :style="item.style || {}"/>
                <span>{{ item.name }}</span>
              </div>
          </div>
        </div>
   
      </div>

    </el-main>
  </el-container>
</template>

<script>
import API from "../api/api_file";
import data from "../data.json";
export default {
    created() {
        this.getAllNode()
    },
    data() {
        return {
            timer: "",
            count: 0,
            srcX: "400",
            srcY: "80",
            realX: "3000",
            realY: "350",
            nodes: [],
            AllNodes: [],
            rebuild: [],
            iconConfig: [
              {
                path: "node-active.png",
                name: "激活状态"
              },
              {
                path: "node-relay.png",
                name: "中继状态"
              },
              {
                path: "star.svg",
                name: "汇聚结点"
              },
              {
                path: "camera.png",
                style: {
                  transform: 'rotateY(180deg)'
                },
                name: "摄像头"
              },
              {
                path: "fengshan.png",
                name: "风机"
              },
              {
                path: "icon1.png",
                name: "通行车道指示灯",
                style: {
                  width: '40px',
                }
              }
            ]
        };
    },
    methods: {
        getAllNode() {
            var that = this;
            // API.getNodes().then(function (res) {
            //     that.AllNodes = res.data.Nodes
            //     that.changeNodesArray()
            //     that.timer = setInterval(that.changeNodesArray, 1000 * 10)  //10s改变一次点的坐标
            // });
            const res = data;
            this.AllNodes = res.data.Nodes
            this.changeNodesArray()
            this.timer = setInterval(that.changeNodesArray, 1000 * 10)  //10s改变一次点的坐标
        },
        changeNodesArray() {
            if (this.count < this.AllNodes.length) {
                this.nodes = this.AllNodes[this.count]
                this.count = this.count + 1
            } else {
                clearInterval(this.timer)
                this.timer = null
            }
        },
        handleNodeX(item) {
            var x = 0;
            var x1 = parseFloat(item.x);
            var x2 = parseFloat(this.srcX);
            var x3 = parseFloat(this.realX);
            x = (x1 / x2) * x3;
            return x.toFixed(2);
        },
        handleNodeY(item) {
            var y = 0;
            var y1 = parseFloat(item.y);
            var y2 = parseFloat(this.srcY);
            var y3 = parseFloat(this.realY);
            y = (y1 / y2) * y3;
            return y.toFixed(2);
        },
        getImage(name) {
          return new URL(`../assets/${name}`, import.meta.url).href;
        },
    },
};
</script>


<style scoped>
.el-container {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  background-color: rgb(7 15 53);
}

.el-header {
  width: 100%;
  position: relative;
  background-image: url(../assets/header.png);
  background-color: rgb(13 7 76);
  background-repeat: no-repeat;
  background-size: contain;
}
.header-title {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  font-size: 25px;
  color: white;
  letter-spacing: 10px;
  justify-content: center;
}
.btn-ul {
  width: 100%;
  height: 40px;
  left: 0;
  margin-top: -30px;
  display: flex;
  justify-content: center;
  color: white;
}

.header-button.active,
.header-button:hover {
  background-image: url(../assets/btn1.png);
}

.header-button {
  height: 80%;
  background-image: url(../assets/btn2.png);
  background-size: cover;
  display: flex;
  align-items: center;
  padding-left: 20px;
}
.el-main {
  width: 100%;
  height: 90%;
  padding: 0;
  margin: 0;
  margin-top: 20px;
}
</style>