import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalController } from '@ionic/angular';
import { AbstractFlatWidget } from 'src/app/shared/genericComponents/flat/abstract-flat-widget';
import {
  ChannelAddress,
  CurrentData,
  Edge,
  EdgeConfig,
  Service,
  Utils,
  Websocket,
} from 'src/app/shared/shared';
import { ChargingStationService } from './chargingstation.service';
import { GridModalComponent } from './modal/modal.component';

@Component({
  selector: 'chargingstation',
  templateUrl: './chargingstation.component.html',
})
export class ChargingStationComponent extends AbstractFlatWidget {
  private static readonly GRID_ACTIVE_POWER: ChannelAddress =
    new ChannelAddress('v2g0', 'GridL1Volts');

  public readonly CONVERT_TO_CELCIUS = Utils.CONVERT_TO_CELCIUS;
  public readonly CONVERT_TO_VOLT = Utils.CONVERT_TO_VOLT;

  status: any;
  temperature: any;
  statusText: String;

  public gridvoltage: number;
  public gridBuyPower: number;
  public gridSellPower: number;
  public gridMode: number;

  protected getChannelAddresses(): ChannelAddress[] {
    let channelAddresses: ChannelAddress[] = [
      ChargingStationComponent.GRID_ACTIVE_POWER,
    ];
    return channelAddresses;
  }
  protected onCurrentData(currentData: CurrentData) {
    this.gridvoltage =
      currentData.allComponents[
        ChargingStationComponent.GRID_ACTIVE_POWER.toString()
      ];
  }

  protected getStatus(res) {
    switch (res) {
      case 0:
        this.statusText =
          'Connector not available, in error state or not installed';
        break;
      case 1:
        this.statusText = 'Connector idle / available';
        break;
      case 2:
        this.statusText = 'Connector plugged, initialising charge';
        break;
      case 3:
        this.statusText = 'Connector plugged, charging in progress';
        break;
      case 4:
        this.statusText = 'Connector plugged, dis-charging in progress';
        break;
      case 5:
        this.statusText = 'Connector plugged, charging finished or paused';
        break;
      default:
        this.statusText =
          'Connector not available, in error state or not installed';
        break;
    }
  }
}

// export class ChargingStationComponent implements OnInit {
//   constructor(
//     private route: ActivatedRoute,
//     private websocket: Websocket,
//     public modalController: ModalController,
//     public service: Service
//   ) {}

//   private static readonly SELECTOR = 'example';

//   private componentId = 'v2g0';

//   private edge: Edge = null;
//   public component: EdgeConfig.Component = null;
//   // constructor(private api: ChargingStationService) {}

//   public readonly CONVERT_TO_CELCIUS = Utils.CONVERT_TO_CELCIUS;
//   public readonly CONVERT_TO_VOLT = Utils.CONVERT_TO_VOLT;

//   status: any;
//   temperature: any;
//   statusText: String;
//   gridvoltage: any;

//   // ngOnInit() {
//   //   this.service.setCurrentComponent('', this.route).then((edge) => {
//   //     this.edge = edge;
//   //     this.service.getConfig().then((config) => {
//   //       this.component = config.getComponent(this.componentId);
//   //       console.log('Component ID' + this.componentId);
//   //       edge.subscribeChannels(this.websocket, this.componentId, [
//   //         new ChannelAddress(this.componentId, 'GridL1Volts'),
//   //         new ChannelAddress(this.componentId, 'LoadPower'),
//   //       ]);
//   //     });
//   //   });

//     protected getChannelAddresses(): ChannelAddress[] {
//       let channelAddresses: ChannelAddress[] = [
//         GridComponent.GRID_ACTIVE_POWER,
//         GridComponent.GRID_MODE,

//         // TODO should be moved to Modal
//         new ChannelAddress('_sum', 'GridActivePowerL1'),
//         new ChannelAddress('_sum', 'GridActivePowerL2'),
//         new ChannelAddress('_sum', 'GridActivePowerL3'),
//       ];
//       return channelAddresses;
//     }
//     protected onCurrentData(currentData: CurrentData) {
//       this.gridvoltage =
//         currentData.allComponents[GridComponent.GRID_MODE.toString()];
//       let gridActivePower =
//         currentData.allComponents[GridComponent.GRID_ACTIVE_POWER.toString()];
//       this.gridBuyPower = gridActivePower;
//       this.gridSellPower = Utils.multiplySafely(gridActivePower, -1);
//     }

//     // ngOnInit(): void {
//     //   setInterval(() => {
//     //     this.getData();
//     //   }, 1000);
//     // }

//     // getData() {
//     //   this.api.gets('status').subscribe((res) => {
//     //     console.log(res);
//     //     this.status = res;
//     //     switch (this.status) {
//     //       case 0:
//     //         this.statusText =
//     //           'Connector not available, in error state or not installed';
//     //         break;
//     //       case 1:
//     //         this.statusText = 'Connector idle / available';
//     //         break;
//     //       case 2:
//     //         this.statusText = 'Connector plugged, initialising charge';
//     //         break;
//     //       case 3:
//     //         this.statusText = 'Connector plugged, charging in progress';
//     //         break;
//     //       case 4:
//     //         this.statusText = 'Connector plugged, dis-charging in progress';
//     //         break;
//     //       case 5:
//     //         this.statusText = 'Connector plugged, charging finished or paused';
//     //         break;
//     //       default:
//     //         this.statusText =
//     //           'Connector not available, in error state or not installed';
//     //         break;
//     //     }
//     //   });
//     //   this.api.gets('temperature').subscribe((res) => {
//     //     console.log(res);
//     //     this.temperature = Math.round(res * 10) / 10;
//     //   });
//     //   this.api.gets('gridvoltage').subscribe((res) => {
//     //     console.log(res);
//     //     this.gridvoltage = parseFloat(res).toFixed(2);
//     //   });
//   }

//   // protected onCurrentData() {
//   //   this.api.gets('users?page=1');

//   // }

//   // async presentModal() {
//   //   const modal = await this.modalController.create({
//   //     component: GridModalComponent,
//   //     componentProps: {
//   //       edge: this.edge,
//   //     },
//   //   });
//   //   return await modal.present();
//   // }
// }
