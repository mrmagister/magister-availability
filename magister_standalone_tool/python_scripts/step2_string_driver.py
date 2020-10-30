import resource
import numpy as np
import sklearn.cluster
import distance
import string
import re
import math
import nltk



def checkString(s):
	
	keywords = ["\\n","\\t","if(","if (",", {","new ","error ","exception","RSA","<html>","<br>","\\*","*?","<yyyy/mm/dd>","*?","\\s*","http:","https:","conf/perspective/","summary","{std}","-socket","interface","npv mode","feature","iscsi","scsiflow","voltage","txpower","rxpower","fxportcapclass","duration","current","iscsi","cigiscs","jboss-","jcommon-","properties","service pack ","c:\\ ","c:\\\\","_end_ports","existing_zone","proxy initiator","/etc/ssh",
			" of ","comma separated"," where ","update ","status=?","where id=?","(hex)","0..255","/etc/","0...255","ext1,bay1","0x123abc,0xabc123","traffic map","isns.entity.mds9000","1..239","21:21:..,","fcalias_symnodename","fcalias_ifc_port","fcalias_domain","domain_list","zone_switch",
			"switch_list","switch_vsan","ivr_to","ivr_active","selected_fabric","transition_vsan","ivr_zone","switch_object_list","transition_switch","_qos_priority","_zone_attribute","row_fcalias","table_zone_member","table_zone_attribute","table_fcalias_member","row_fcalias_member","row_zone_attribute",
			"oper.zoning.aliaslist","row_zone_member","$default_zones$","$default_zone$",
			"fabric_binding","empty_string","switch & port","domain & port",
			"label_switch_add","zone membership","zones membership","target_zones",
			"triallicense","read_io_"," discovered","erase (",
			"queued (","<lookup>","alter table ",": true}",
			"server.forward.event.","select key","insert into ",".setup.msi",
			"jfreechart-","lucene-core-","postgresql-","select version()",
			"select role from ","select * ","select vrf","save_config",
			"iscsi_controller","/ECB/PKC","product authorization","_route_switch",
			"_fabric_switch","channel_director","from_endport_table","storage_enclosure",
			"ucs_blade_mgmt_if","from_host_enclosure","install","vm_datastore_info",
			"vm_phynic_info","domain_info",	"endport_info","bootflash?",
			"bootflash:%s","ejb3-persistence","select count","select ip_addr",
			"select expire "," ifindex","outdiscard_stats","default_scp_repository",
			"default_repository","segment_pool","network_vlan_pool","resource_vlan",
			"vlan_min","vlan_max","cannot convert",	"invalid serial","entered loopback",
			"invalid dot1q id:","entered network vlan",	"entered vrf vlan","network name %s",
			"invalid network","network id",	"invalid vrf","all status deleted","have overlay attached",
			"have policy attached","port_channel_id","loopback_id","top_down_l3_",
			"top_down_network","type=%s","%sgeneratekey()","uuid association is not",
			"not able to convert","invalid uuid","<slot","%sgetuuidbyserialnumber","converttouuidbased",
			"with vrfatt as","started compliance check","invalid ipv4","device_not_reachable",
			"a-z0-9","a-f0-9","<for_variable>","<string_literal>","<plain_literal>","and switch_port ",
			"extension_values=null","multisite_overlay","and switch_sn ","device_unreachable","please unattach all",
			"network to l2","select e","template variables","device_img_version","sshv2 not available",
			"objectclass=network","connection is lost","to the server",	"<supported_platforms>","<unsupported_platforms>",
			"<platform>","expirationtime","ipv4address","switchrole",".networkattach.dot",".discover.snmpv3authprotocol",
			".l3.extensionfabricname","dcnm.serial_numbers","mozilla/5.0","eth 1/3-5",
			"parameter_desc","property_value","string[]","ipaddress[]",
			"string_value","quoted_string",	"annotation_value","bootflash:",
			"lan_cpu_memory","lan1 fpfabric1",
			"lan_cpu_process","lan_cpu_toggle",
			"role_fabric_info", "san_health_fabric",
			"san_health_switch","san_health_performance",
			"san_health", "/_cat/indices/fc_*", "trained_baseline",	"telemetry",
			"san_ect_baseline",	"job_type",	"web_report","analysis_report",
			"rollback runnning-config",	"connection is closed",	"invalid command"," | grep ",
			"continue_on_error","template variables","switchaction","select document_name",
			"job_type_web_report","order by last_seen",	"stat.total","and (upper",
			"and (not events","n9k-112-spine,n9k-15","n9k-112-spine","maintenance_job",
			"order by stat","san_ect_baseline",	" and (is_up = ","zoneaction","pkcs_key_pair_gen","ckm_dsa",
			"ckm_des","ckm_x9",	"ckm_rc2_","ckm_dh","ckm_vendor","ckm_x9_42",
			"ckm_tls_master","ckm_tls_prf", "ckm_rsa", "ckm_cdmf","ckm_aes","ckm_twofish",
			"ckm_blowfish",	"ckm_batom_","ckm_skipjack",	"cmk_juniper",
			"ckm_kea_",	"ckm_ec",
			"md5_cast_cbc",	"ckm_key_wrap",
			"_cast_cbc","key_derivation",
			"ckm_pbe_",	"ckm_pba",
			"ckm_ripemd","master_key_derive",
			"key_and_mac_",	"ckm_sha",
			"flows_rollup",	"extract_key_from_key",
			"ckm_sha","mac_general","xor_base_and_","_concatenate_","ckm_cast",	"_rsa_pkcs","stat.type=","fabric.license","completed_with_error",
			"template content",	"config_",
			".data_list_stmt", "stat.type=3",
			" OR ",	"select document_name",
			"total isls","<sup>",
			"healthscore", "stat.total_discard",
			"zonetotalgs3",	"select token",
			"cpmprocext","_search?scroll",
			"search/scroll","lan_power_stats",
			"lan_resources_stats","_cfg/_search",
			"default_truststore","text/xml",
			"dfa.dot1q","</sup>",
			"no fcip","[a-z,",
			"export_csv","npu_load",
			"template.in_use.check","wwnmtype",
			"_member_type","computersystem",
			"storagehardwareid","fcnameserverfc4type",
			"sys_name asc",	"order by ",
			" and (not","(MB/S)",
			"</sup>","nodeipaddress",
			"include_vrfname","include_vrfsegmentid",
			"include_service","is_metaswitch",
			"objectclass=",	"ndj.job_id = ?",
			"banner motd ",	"[0-9]",
			"a-zA-Z0-9","status successfully",
			"include_peeripaddress","include_peer",
			"overlay.data",	"sender handle:",
			"invalid fabric ","default_lan",
			"default_san","/usr/local/",
			"name = ?",	"unicast_bw",
			"a-zA-Z","row_vlanbrief",
			"pmnstaticreceivercontroller","hello, world!: ",
			"row_mtuinfo","bandwidthquer",
			"deletehostpolicyassociation","policyassociation",
			"discovered_endpoint_snapshot",	"match_all",
			"pmn_switch_keep_alive_snapshot","DB(graph):",
			"pmn_receiver_in","discovered_endpoint",
			"pmn_flows_snapshot","pmn_flows_history",
			"is_channel = ","is_chanel = ",
			"pmn_sender_only_flow",	"isnotified","fmgr-device",	"\\d+",	"role = ?",	"\\S+",	"notificationbroker","pmnpathfinder","pmnappliedhostpolicyquerier",	"add_user xmpp -u",	"default_scp_repository","default_network_extension","enhanced_fabric_mgmt",	"is set for the device ","\\D+",	"show spannig-tree","underlay_autoconfig","gwy_connections","dcnm_version","dcnmcommonendpointconfigurator","vnirange","bootstrap_enable",	"dhcp_enable","upgrade_from",	"default_vrf_extension","migration.completed", "(objectclass=host)","select orch_id","link -->","serialnumber:%s;entityname","show resource ", "threadpooldetails","flows_snapshot","flowstatus","{placeholder}","select distinct ","pmnesquerybuilder","gressifindex",	"reporterip","tengigbase","flow_stats_","sortfieldholder","orderholder"," and sys_name like","notificationbroker", " and isl.is_present = ","performance N3K buffer","${sw}\\","initiator_switch","host (initiator)", "storage (target)",	"unconfig_","ping_switch"," switch)","switch_info","pmnstaticreceiverhandler",
			"pmn_flow",	"max_bandwidth","min_bandwidth","switch_vfc",".properties","yyyy.mm.dd", "hh:mm:ss",	"rndtrip_time",	"fm_tacacs","fm_radius","fm_ldap","cfs_master_","phone_number",	"contact_email","step ","use snmpv3","(free/total)",	"eth_adapter","_bandwidth","_time","apply_security","ipsec_default_transform",	"security_capable",	"security_controller",	"ficon_enabled",".log",	"fcfxport",	"delete from ",	"physical_logic","lanswitch_map","ethswitch_fex","-type ","rx bytes",	"tx bytes",	"portsupported","connunitport",	"-contact",	" traceroute","text_commands","commands_vbl",	"bcprov-jdk","commons-net-","cficonport", "connection timed out","cores failed",	"aaa_accounting_message","inconsistancy_map","fcnameserver",	"lanswitch_uuid","port: %s","state: %s","reason: %s","elastic-search-service","/usr/libexec/heartbeat","connunitsns","ipv6 address","en_us.utf-8","max_replication_lag","ha.node.",	"lan_neighbor", "connunitport",	"physical_index","relative_position","caaaloginauthtypemschapv2","(fcip ports)",	"device_alias_name","switch, module","zone_member_type","fcalias_member_type","disable_ivr2_list","enable_ivr2_list",	"audit_purge_job","select 1 from ","switch_credentials","select group_id,",	"(fc ports)","_revision","vendor_type","_port_index",	"sec_serial_number","hw_component_id","enclosure_id",
			"select hw_component_id from ",	"hw_power_supply","capacitymanager.",	" and s.serial_number = ?",	"dmm_ssn16_pkg","license file",	"license source ","license found",	"not disconnect","base_svc_cluster","enc_datastore_info","ucs_ethport_err_stats","child_count","_status_cause",	"unsupported operation","connect timed out","cfs_region","status_description", "last_scan_time","last_update_time",	"last_change_time",	"power_admin_status","redundancy_mode","channelmember",	"power_units","license_flag","max_license","model_description",	"opt_rom_version","grace_period", "power_oper_status","nav_group","select id","device_access","logicsvr_fabric","? where ","select switch","select name ",	".rules","(sec)","vsan ids",	"configed_","zone_members",	"cspanvsan","interval", "lifetime",	"lifesize",	"cpkicert",	"vlan(vsan)","cspansource",	"actual input",	"slot/port","telnet","iscsi - tcp",	"snmp - udp","command(s):",	"sftp userpassword:","comma separated",	"builds","reconfigures","(FCIP ports)",	"(FC ports)","builds (bf)","reconfigures (rcf)","(portchannel)","virtualdomains","topology","fc2iscsi","snmp4j-","_accessport",	"_trunksport","_allport","jsch-","cfmfcip",	"csiSession","_job_object",	"<a name=","<a href=","lan_fcimport","fcif","cpsmfabric","singlefabric", "zonesml","vlantrunk","selected_","ipsec_default_","fieldlabel","statuscmd","cmd_","pchnl_","_pchnl","ivr_selected","ficon_enabled","npv_mode","(seed switch)","(mb/sec)",	"classmap","(mb)","inprogress checking", "version_string","running software version","confirm new password","inboundip",
			"outboundip","dot3stats","sample (sec)","delay (sec)","(mbps)","ciscoscsiflow",	" radius","wbem-http","commons-codec-","commons-discovery-","commons-logging-","wsdl4j-",
			"?wsdl","_zoneset","zonetx","zonerx","zonesetzone","zonesetenforced","$ignore_community$","show lldp ","show vdc ","show mac ","show int ","show ip",
			"show vpc ","show vrc ","show vlan ","show nve ","-vlan scope","cachetimeout",	"timeout","show nve ","(id, size)","(id,size)",	"routemetric","dmmswitch",
			"cfcsp","ingress","# of active jobs","!command: check",	"fabric database","show run ", "sh fabricpath","sh l2route","show spanning ","show fex ","show vrf ","show logging ",
			"head lines ",".zonesetlist."," pdt ","type <cr> ",	"ip subnet(v4)","transport.udpsession",	"transport.tcpsession","transport.proxysession","spanning-tree","alias->","zone->alias","ip subnet(v6)",
			"icons/"," id from ","application/","select document_name",	"image/","switch/module","vijava2u","commons-lang-","snmp-trap","normal-service","monetary-cost","echo-reply","unreachable","holdtime ","pg_hba.conf","_policy","connunitpor","password needs to be","confirm password","ethswitch_vlan","switch_port","host_cluster","ucs_ethport_mcast_stats","jpg1906002f:edge-1","ethisl_vlan","protocol","source","token not present","-t $title -e $cmd","cmd vdc","pm_dataindex","pm_extraoid","pm_indexbookmark","pm_collection","snmp_community","version1.0","span_source_port","\\w"," from ","gnome-terminal","logvariable","config terminal","configure terminal","show cli list","unknown_controller","bad port value","changing the port","continue?","\\s{1,}","\\d{1,}","ipv6","(0=global)","server_pkg","capacity (","npiv (","(core)","port vsan","continue?","already enabled?",	"storage_services_","already exist?","quality of service","ssh ","3270 regime","select max","failed server","failed port","rrd, version",	"rrd version","assertion failed","valid license","license file","move server","invalid value","{0} has duplicate {1}","device {0}","value is not specified","invalid or expired session","authentication failed",	"invalid response from","invalid {0}","multiple instances","operation is not supported","invalid value","device {0} does not support","license folder","value is not specified","not specified","inconsistent value","does not support","{0} {1}.","{0} {1} cannot","inconsistent value","choose old port","<?xml ",]	
	
	for keyword in keywords:
		keyword = keyword.replace("\"","")
		keyword = keyword.replace('"',"")
		#line.replace("\n","")
		keyword = re.sub(r"\s+", "", keyword)
		if keyword in s:
			return True
	
	return False

def isascii(s):
	return len(s) == len(s.encode())

def entropy(string):
        #"Calculates the Shannon entropy of a string"

        # get probability of chars in string
        prob = [ float(string.count(c)) / len(string) for c in dict.fromkeys(list(string)) ]

        # calculate the entropy
        entropy = - sum([ p * math.log(p) / math.log(2.0) for p in prob ])
        print("string: " + string + ": entropy: " + str(entropy))
        return entropy

def main(arg1):

    import glob
    incoming_path = arg1
    files = glob.glob(incoming_path)

    my_array = []
    my_set = set()
    for file in files:
        print(file)
        with open(file) as my_file:
            i = 1
            for line in my_file:
            
                line = line.replace("\"","")
                line = line.replace('"',"")
                line = re.sub(r"\s+", "", line)
            
                val = isascii(line)
            
                if val:
                    if checkString(line) is False:
                        if entropy(line) > 3.2:
                            flag = line in my_set
                            if flag is False:
                                my_set.add(line)
                                my_array.append(line)
                                print("line #" + str(i) + " : "+ line)
            
                i = i + 1
        
            
            print("total number of strings " + str(i) + " : number of candidates " + str(len(my_array)))
        print("checkpoint 1")

        words = np.asarray(list(my_array)) #converting from set to array

        print("checkpoint 2")

        lev_similarity = -1*np.array([[distance.jaccard(w1,w2) for w1 in words] for w2 in words])
        
        print("checkpoint 3")
        affprop = sklearn.cluster.AffinityPropagation(affinity="precomputed", damping=0.5)
        affprop.fit(lev_similarity)

        print("checkpoint 4")
        file.replace(".txt","-extra.txt")
        with open(file, "w") as f:
            for cluster_id in np.unique(affprop.labels_):
                exemplar = words[affprop.cluster_centers_indices_[cluster_id]]
                cluster = np.unique(words[np.nonzero(affprop.labels_==cluster_id)])
                cluster_str = ", ".join(cluster)
                print(" - *%s:* %s" % (exemplar, cluster_str))
                f.write("\n - *%s:* %s" % (exemplar, cluster_str))

import sys

if __name__ == "__main__":
    main(sys.argv[1])
